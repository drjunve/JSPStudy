package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

//JDBC를 이용한 DB연결을 위해 클래스 상속
public class BoardDAO extends JDBConnect {
	
	//인수생성자에서는 application 내장객체를 매개변수로 전달한다.
	public BoardDAO(ServletContext application) {
		/* 부모 생성자에서는 aplication을 통해 web.xml에 직접
		 * 접근하여 컨텍스트 초기화 파라미터를 얻어온다. */
		super(application);
	}
	
	//게시물의 갯수를 카운트하여 int형으로 반환한다.
	public int selectCount(Map<String, Object>map) {
		
		//게시물의 갯수를 반환하기 위한 변수
		int totalCount = 0;
		
		//게시물 수를 얻어오기 위한 쿼리문 작성
		String query = "SELECT COUNT(*) FROM board";
		
		/* 검색어가 있는 경우 where 절을 추가하여 조건에 맞는 게시물만
		 * 인출한다. */
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			//정적쿼리문 실행을 위한 Statement객체 생성
			stmt = con.createStatement();
			//쿼리문 실행 후 결과는 ResultSet으로 반환한다.
			rs = stmt.executeQuery(query);
			//커서를 첫번째 행으로 이동하여 레코드를 읽는다.
			rs.next();
			//첫번째 컬럼(count함수)의 값을 가져와서 변수에 저장한다.
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/* 작성된 게시물을 인출하여 반환한다. 특히 반환값은 여러개의 레코드를 
	 * 반환할 수 있고, 순서를 보장해야 하므로 List 컬렉션을 사용한다. */
	public List<BoardDTO> selectList(Map<String, Object> map) {
		
		/* List계열의 컬렉션으로 생성한다. 이때 타입 매개변수는 board테이블을
		 * 대상으로 하므로 BoardDTO로 설정한다. */
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		/* 레코드 인출을 위한 select 쿼리문 작성. 최근 게시물이 상단에
		 * 출력되야 하므로 일련번호의 내림차순으로 정렬한다. */
		String query = "SELECT * FROM board ";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC ";
		
		try {
			//쿼리 실행 및 결과셋 반환
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			//2개 이상의 레코드가 반환될 수 있으므로 while문을 사용한다.
			while (rs.next()) {
				//하나의 레코드를 저장할 수 있는 DTO객체를 생성한다.
				BoardDTO dto = new BoardDTO();
				
				//setter를 이용해서 각 컬럼의 값을 멤버변수에 저장한다.
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				//List에 DTO를 추가한다.
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 줄 예외 발생");
			e.printStackTrace();
		}
		
		//인출한 레코드를 저장한 List를 호출한 지점으로 반환한다.
		return bbs;
	}
	
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = "INSERT INTO board ( "
					+ " num,title,content,id,visitcount) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL, ?, ?, ?, 0";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = "SELECT B.*, M.name "
				+ " FROM member M INNER JOIN board B "
				+ " ON M.id=B.id "
				+ " WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,  num);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	public void updateVisitCount(String num) {
		String query = "UPDATE board SET "
				+ " visitcount=visitcount+1"
				+ " WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);;
			psmt.executeQuery();
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
}

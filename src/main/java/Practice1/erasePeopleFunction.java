package Practice1;

import java.util.Scanner;

public class erasePeopleFunction {

	Scanner sc = new Scanner(System.in);
	
	int N;
	int K;
	
	int people[];
	int peopleNum = 1;
	
	public void getNumber() {
		
		System.out.println("원탁 위에 1번부터 N번까지의 사람이 앉아있다.");
		System.out.println("당신이 정수 K를 입력하면 K번째 사람을 제거한다.");
		System.out.println("한 사람이 제거 되면 그 사람을 기준으로 다시 K번째 사람이 제거되고");
		System.out.println("이 과정은 모든 사람이 제거될 때까지 계속된다.");
		System.out.println("N과 K를 입력하라.");
		
		System.out.print("N : ");
		N = sc.nextInt();
		System.out.print("K : ");
		K = sc.nextInt();
		
		for (int i : people) {
			people[i] = peopleNum;
			peopleNum++;
		}
		
	}
	
	public void eraseChoice() {
		
		System.out.println("원탁 위에 사람이 배정되었습니다.");
		System.out.println("제거를 시작하시겠습니까?");
		System.err.print("Y or N");
		String yorn = sc.next();
		
		if(yorn == "Y") {
			erasePeople();
		}
		else if(yorn == "N") {
			System.out.println("아무도 제거되지 않았습니다. 처음부터 다시 시작합니다.");
			getNumber();
		}
		
	}
	
	public void erasePeople() {
		
		for (int i : people) {
			people[K] = 0;
			System.out.println(K+"번 사람이 제거되었습니다.");
			K += K;
			if(K>people.length) {
				K = (K-(people.length));
			}
		}
		System.out.println("모두 제거 되었습니다.");
	}
	
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 프로그램 실행
        System.out.println("=== 융합관 강의실 좌석 사전 선택 프로그램 ===");

        // 이름 입력
        System.out.println("이름을 입력하세요: ");
        String name = scanner.nextLine();   // 사용자로부터 문자열(이름) 입력 받기

        // 학번 입력
        System.out.println("학번을 입력하세요: ");
        String studentId = scanner.nextLine();  // 사용자로부터 문자열(학번) 입력 받기

        // 입력한 값 출력
        System.out.println("\n=== 입력한 정보를 확인하세요. ===");
        System.out.println("이름: " + name);
        System.out.println("학번: "+ studentId);

        scanner.close();    // 스캐너 종료 (메모리 낭비 방지)
    }
}
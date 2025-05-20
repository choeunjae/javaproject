import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 프로그램 실행
        System.out.println("=== 융합관 강의실 좌석 사전 선택 프로그램 ===");

        // 1. 이름 입력
        System.out.print("이름을 입력하세요: ");
        String name = scanner.nextLine();   // 사용자로부터 문자열(이름) 입력 받기

        // 2. 학번 입력
        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine();  // 사용자로부터 문자열(학번) 입력 받기

        // 3. 강의실 선택
        boolean vaildRoom = false;
        int roomNumber = 0;
        String room = "";

        while (!vaildRoom) {
            System.out.println("강의실을 선택하세요.");
            System.out.println("1. 201호");
            System.out.println("2. 202호");
            System.out.println("3. 212호");
            System.out.print("번호 입력: ");

            roomNumber = scanner.nextInt();

            if (roomNumber == 1) {
                room = "201";
                vaildRoom = true;
            } else if(roomNumber == 2) {
                room = "202";
                vaildRoom = true;
            } else if(roomNumber == 3) {
                room = "212";
                vaildRoom = true;
            } else {
                System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
            }
        }

        // 입력한 값 출력
        System.out.println("\n=== 입력한 정보를 확인하세요. ===");
        System.out.println("이름: " + name);
        System.out.println("학번: "+ studentId);
        System.out.println("선택한 강의실: " + room + "호");

        scanner.close();    // 스캐너 종료 (메모리 낭비 방지)
    }
}
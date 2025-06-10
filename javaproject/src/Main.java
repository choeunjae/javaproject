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
        boolean validRoom = false;
        int roomNumber = 0;
        String room = "";

        while (!validRoom) {
            System.out.println("\n강의실을 선택하세요.");
            System.out.println("1. 201호");
            System.out.println("2. 202호");
            System.out.println("3. 212호");
            System.out.print("번호 입력: ");

            if (scanner.hasNextInt()) {
                roomNumber = scanner.nextInt();
                scanner.nextLine();

                switch (roomNumber) {
                    case 1 -> { room = "201"; validRoom = true; }
                    case 2 -> { room = "202"; validRoom = true; }
                    case 3 -> { room = "212"; validRoom = true; }
                    default -> System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
                }
            } else {
                System.out.println("숫자를 입력해주세요.");
                scanner.nextLine(); // 잘못된 입력 제거
            }
        }

        // 좌석 선택 기능 추가
        int rows, cols;
        if (room.equals("212")) {
            rows = 4;
            cols = 8;
        } else {
            rows = 7;
            cols = 8;
        }


        // 2차원 배열 생성 및 초기화
        String[][] seats = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (room.equals("212")) {
                    seats[i][j] = "O"; // 212호는 모두 좌석 있음
                } else {
                    // 201/202호: 마지막 줄(6번째 인덱스) 오른쪽 4자리는 없음("-")
                    if (i == 6 && j >= 4) seats[i][j] = "-";
                    else seats[i][j] = "O";
                }
            }
        }

        // 현재 좌석 배치도 출력
        System.out.println("\n=== 좌석 배치도 ===");
        printSeats(seats);

        boolean seatSelected = false;
        int selectedRow = -1;
        int selectedCol = -1;

        // 좌석 선택 루프
        while (!seatSelected) {
            System.out.print("예약할 줄 번호(1~" + rows + "): ");
            int r = scanner.nextInt() - 1;
            System.out.print("예약할 열 번호(1~" + cols + "): ");
            int c = scanner.nextInt() - 1;

            // 201/202호의 7번째 줄 오른쪽 네 자리 입력 시 유효하지 않음 처리
            if (room.equals("201") || room.equals("202")) {
                if (r == 6 && c >= 4) {
                    System.out.println("유효하지 않은 좌석입니다. 다시 시도하세요.");
                    continue;
                }
            }

            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                System.out.println("유효하지 않은 좌석입니다. 다시 시도하세요.");
            } else if (seats[r][c].equals("X")) {
                System.out.println("이미 예약된 좌석입니다. 다른 좌석을 선택하세요.");
            } else {
                seats[r][c] = "X";                 // 좌석 예약
                seatSelected = true;
                selectedRow = r + 1;
                selectedCol = c + 1;
                System.out.println("좌석 예약이 완료되었습니다!");
            }
        }

        // 예약 후 최종 좌석 배치도 출력
        System.out.println("\n=== 최종 좌석 배치도 ===");
        printSeats(seats);

        // 입력한 값 출력
        System.out.println("\n=== 입력한 정보를 확인하세요. ===");
        System.out.println("이름: " + name);
        System.out.println("학번: "+ studentId);
        System.out.println("선택한 강의실: " + room + "호");
        System.out.println("선택한 좌석: " + selectedRow + "줄 " + selectedCol + "열");

        scanner.close();    // 스캐너 종료 (메모리 낭비 방지)
    }

    public static void printSeats(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            System.out.print((i + 1) + "줄: ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print("[" + seats[i][j] + "]");
                if (j == 3) System.out.print("  "); // 왼쪽4자리 오른쪽4자리 사이 공백 추가
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}
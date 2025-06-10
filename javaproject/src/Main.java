import java.util.*;

public class Main {
    
    // 예약 정보 저장 클래스
    static class Reservation {
        String name;
        String studentId;
        String room;
        int row;
        int col;
        
        public Reservation(String name, String studentId, String room, int row, int col) {
            this.name = name;
            this.studentId = studentId;
            this.room = room;
            this.row = row;
            this.col = col;
        }
    }
    
    static List<Reservation> reservations = new ArrayList<>();  // 모든 예약 저장
    static Map<String, String[][]> roomSeats = new HashMap<>(); // 강의실별 좌석 상태
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 프로그램 실행
        System.out.println("=== 융합관 강의실 좌석 사전 선택 프로그램 ===");

        // 1. 이름 입력 (종료 조건)
        while (true) {
            System.out.print("\n이름을 입력하세요 (종료를 원하면: x): ");
            String name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("x")) {
                showStatistics();
                break;
            }

            // 2. 학번 입력
            System.out.print("학번을 입력하세요: ");
            String studentId = scanner.nextLine();  // 사용자로부터 문자열(학번) 입력 받기

            // 3. 강의실 선택
            String room = selectRoom(scanner);

            // 강의실 좌석 정보 2차원 배열로 출력
            System.out.println("\n=== 현재 " + room + "호 좌석 배치도 ===");
            if (!roomSeats.containsKey(room)) {
                initializeRoomSeats(room);
            }
            printSeats(roomSeats.get(room));

            int[] seat = processSeatSelection(scanner, room);

            reservations.add(new Reservation(name, studentId, room, seat[0], seat[1]));

            // 예약자 정보 출력
            System.out.println("\n=== 예약 정보 ===");
            System.out.println("이름: " + name);
            System.out.println("학번: " + studentId);
            System.out.println("강의실: " + room + "호");
            System.out.println("좌석: " + seat[0] + "줄 " + seat[1] + "열");

            // 계속 진행 여부 확인
            while (true) {
                System.out.print("\n계속하시려면 'a', 종료하시려면 'b' 입력: ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("a")) {
                    break; // 다음 예약 진행
                } else if (choice.equals("b")) {
                    showAllSeats();
                    scanner.close();
                    System.exit(0);
                } else {
                    System.out.println("잘못된 입력입니다!");
                }
            }
        }
        scanner.close();
    }

    // 강의실 선택 메서드
    private static String selectRoom(Scanner scanner) {
        boolean validRoom = false;
        String room = "";

        while (!validRoom) {
            System.out.println("\n강의실을 선택하세요.");
            System.out.println("1. 201호");
            System.out.println("2. 202호");
            System.out.println("3. 212호");
            System.out.print("번호를 입력하세요: ");

            if (scanner.hasNextInt()) {
                int roomNumber = scanner.nextInt();
                scanner.nextLine();

                switch (roomNumber) {
                    case 1 -> { room = "201"; validRoom = true; }
                    case 2 -> { room = "202"; validRoom = true; }
                    case 3 -> { room = "212"; validRoom = true; }
                    default -> System.out.println("잘못된 번호입니다. 다시 입력하세요.");
                }
            } else {
                System.out.print("숫자를 입력하세요: ");
                scanner.nextLine();
            }
        }
        return room; // 선택된 강의실 반환
    }

    // 좌석 선택 및 처리 메서드
    private static int[] processSeatSelection(Scanner scanner, String room) {
        // 강의실 좌석 초기화 (최초 1회만 실행)
        if (!roomSeats.containsKey(room)) {
            initializeRoomSeats(room);
        }
        String[][] seats = roomSeats.get(room);

        int[] seatPos = selectValidSeat(scanner, room, seats);
        return seatPos;
    }

    // 강의실별 좌석 초기화
    private static void initializeRoomSeats(String room) {
        int rows = room.equals("212") ? 4 : 7;
        int cols = 8;
        String[][] seats = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (room.equals("201") || room.equals("202")) {
                    seats[i][j] = (i == 6 && j >= 4) ? "-" : "O";
                } else {
                    seats[i][j] = "O";
                }
            }
        }
        roomSeats.put(room, seats);
    }

    // 유효 좌석 선택
    private static int[] selectValidSeat(Scanner scanner, String room, String[][] seats) {
        while (true) {
            System.out.print("예약할 줄 번호를 입력하세요. (1~" + seats.length + "): ");
            int r = scanner.nextInt() - 1;
            System.out.print("예약할 열 번호를 입력하세요. (1~" + seats[0].length + "): ");
            int c = scanner.nextInt() - 1;
            scanner.nextLine(); // 버퍼 클리어

            // 201/202호 7번째 줄 오른쪽 4자리 차단
            if ((room.equals("201") || room.equals("202")) && r == 6 && c >= 4) {
                System.out.println("유효하지 않은 좌석입니다.");
                continue;
            }

            // 유효성 검사
            if (r < 0 || r >= seats.length || c < 0 || c >= seats[0].length) {
                System.out.println("범위를 벗어난 좌석입니다.");
                continue;
            }

            // 좌석 상태 확인
            if (seats[r][c].equals("X")) {
                System.out.println("이미 예약된 좌석입니다.");
            } else if (seats[r][c].equals("-")) {
                System.out.println("존재하지 않는 좌석입니다.");
            } else {
                seats[r][c] = "X";
                return new int[]{r + 1, c + 1}; // 실제 줄/열 번호 반환
            }
        }
    }

    // 모든 강의실 좌석 출력
    private static void showAllSeats() {
        System.out.println("\n=== 모든 강의실 최종 좌석 배치 ===");
        for (String room : roomSeats.keySet()) {
            System.out.println("\n[" + room + "호 좌석 배치]");
            printSeats(roomSeats.get(room));
        }
    }

    // 통계 출력 메서드 (예약자 목록)
    private static void showStatistics() {
        System.out.println("\n=== 예약 통계 ===");
        Map<String, List<Reservation>> stats = new HashMap<>();
        for (Reservation r : reservations) {
            stats.putIfAbsent(r.room, new ArrayList<>());
            stats.get(r.room).add(r);
        }
        for (String room : stats.keySet()) {
            System.out.printf("\n[%s호 예약 현황]\n", room);
            for (Reservation r : stats.get(room)) {
                System.out.printf("- %s (%s): %d줄 %d열\n",
                        r.name, r.studentId, r.row, r.col);
            }
            System.out.println("총 " + stats.get(room).size() + "건");
        }
    }

    // 좌석 출력 메서드
    public static void printSeats(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            System.out.print((i + 1) + "줄: ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print("[" + seats[i][j] + "]");
                if (j == 3) System.out.print("  ");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}
import java.util.Scanner;

public class Day12 {

	public static enum Direction {
		N, E, S, W {
			@Override
			public Direction next() {
				return values()[0];
			};
		};
		public Direction next() {
			return values()[ordinal() + 1];
		}
	};

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(12, "jason");
		String data = sb.toString();

		Ferry ferry = new Ferry(Direction.E, 0, 0);
		ferry.navigate(data);
		System.out.println("first answer = " + ferry.distance());

		ferry = new Ferry(Direction.E, 0, 0);
		ferry.navigateWithWaypoint(data);
		System.out.println("second answer = " + ferry.distance());

	}

	private static class Ferry {
		private static final int ROT_INC = 90;
		private Direction dir;
		private int xVal;
		private int yVal;
		private int[] waypoint;

		public Ferry(Direction d, int x, int y) {
			xVal = x;
			yVal = y;
			dir = d;
			waypoint = new int[2];
			waypoint[0] = 10;
			waypoint[1] = 1;
		}

		public void navigateWithWaypoint(String dataString) {
			Scanner s = new Scanner(dataString);
			while (s.hasNextLine()) {
				String cmd = s.nextLine();
				int value = Integer.parseInt(cmd.substring(1));
				switch (cmd.charAt(0)) {
				case 'N':
					mvWayNorth(value);
					break;
				case 'S':
					mvWaySouth(value);
					break;
				case 'E':
					mvWayEast(value);
					break;
				case 'W':
					mvWayWest(value);
					break;
				case 'L':
					rtWayLeft(value);
					break;
				case 'R':
					rtWayRight(value);
					break;
				case 'F':
					for (int i = 0; i < value; i++) {
						moveToWaypoint();
					}
					break;
				}
			}
			s.close();
		}

		private void moveToWaypoint() {
			xVal += waypoint[0];
			yVal += waypoint[1];
		}

		private void rtWayRight(int value) {
			for (int i = 0; i < value; i += ROT_INC) {
				int temp = waypoint[0];
				waypoint[0] = waypoint[1];
				waypoint[1] = temp * -1;
			}
		}

		private void rtWayLeft(int value) {
			for (int i = 0; i < value; i += ROT_INC) {
				int temp = waypoint[0];
				waypoint[0] = waypoint[1] * -1;
				waypoint[1] = temp;
			}
		}

		private void mvWayWest(int value) {
			waypoint[0] -= value;
		}

		private void mvWayEast(int value) {
			waypoint[0] += value;
		}

		private void mvWaySouth(int value) {
			waypoint[1] -= value;
		}

		private void mvWayNorth(int value) {
			waypoint[1] += value;
		}

		public void navigate(String dataString) {
			Scanner s = new Scanner(dataString);
			while (s.hasNextLine()) {
				String cmd = s.nextLine();
				int value = Integer.parseInt(cmd.substring(1));
				switch (cmd.charAt(0)) {
				case 'N':
					moveNorth(value);
					break;
				case 'S':
					moveSouth(value);
					break;
				case 'E':
					moveEast(value);
					break;
				case 'W':
					moveWest(value);
					break;
				case 'L':
					rotateLeft(value);
					break;
				case 'R':
					rotateRight(value);
					break;
				case 'F':
					moveForward(value);
					break;
				}
			}
			s.close();
		}

		public void moveForward(int value) {
			switch (dir) {
			case N:
				moveNorth(value);
				break;
			case S:
				moveSouth(value);
				break;
			case E:
				moveEast(value);
				break;
			case W:
				moveWest(value);
				break;
			}
		}

		public void moveNorth(int change) {
			this.yVal += change;
		}

		public void moveSouth(int change) {
			this.yVal -= change;
		}

		public void moveEast(int change) {
			this.xVal += change;
		}

		public void moveWest(int change) {
			this.xVal -= change;
		}

		public void rotateRight(int change) {
			for (int i = 0; i < change; i += ROT_INC) {
				chgDir(dir.next());
			}
		}

		public void rotateLeft(int change) {
			for (int i = 0; i < change; i += ROT_INC) {
				chgDir(dir.next().next().next());
			}
		}

		public int distance() {
			return Math.abs(xVal) + Math.abs(yVal);
		}

		public void chgDir(Direction d) {
			dir = d;
		}

	}

}

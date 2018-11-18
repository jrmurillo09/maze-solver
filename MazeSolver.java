/*  Jose Murillo
	cssc0067
*/
import data_structures.*;

public class MazeSolver{
	private Stack<GridCell> stack;
	private Queue<GridCell> queue;
	private MazeGrid maze;
	private int dimension;
	
	public MazeSolver(int dimension){
		this.dimension = dimension;
		stack = new Stack<GridCell>();
		queue = new Queue<GridCell>();
		maze = new MazeGrid(this,dimension);
	}
	
	public void mark(){
		GridCell start = maze.getCell(0, 0);;
		start.setDistance(0);
		maze.markDistance(start);
		queue.enqueue(start);
		while(!queue.isEmpty()){
			GridCell tmp = queue.dequeue();
			int x = tmp.getX();
			int y = tmp.getY();
			int dist = tmp.getDistance()+1;
			GridCell up = maze.getCell(x - 1, y );
			GridCell down = maze.getCell(x + 1, y);
			GridCell left = maze.getCell(x, y - 1);
			GridCell right = maze.getCell(x, y + 1);
			if(maze.isValidMove(up) && !up.wasVisited()){
				up.setDistance(dist);
				maze.markDistance(up);
				queue.enqueue(up);
			}
			if(maze.isValidMove(down) && !down.wasVisited()){
				down.setDistance(dist);
				maze.markDistance(down);
				queue.enqueue(down);
			}
			if(maze.isValidMove(left) && !left.wasVisited()){
				left.setDistance(dist);
				maze.markDistance(left);
				queue.enqueue(left);
			}
			if(maze.isValidMove(right) && !right.wasVisited()){
				right.setDistance(dist);
				maze.markDistance(right);
				queue.enqueue(right);
			}
		}
	}
	
	public boolean move(){
		GridCell end = maze.getCell(dimension - 1,dimension - 1);	
		GridCell currCell = maze.getCell(dimension - 1, dimension - 1);
		int distance = end.getDistance();
		if(distance == -1) return false;  
		stack.push(end); 
		while(distance != 0) {
			currCell.getDistance();
			currCell = stack.peek();
			int x = currCell.getX();
			int y = currCell.getY();
			GridCell up = maze.getCell(x - 1, y );
			GridCell down = maze.getCell(x + 1, y);
			GridCell left = maze.getCell(x, y - 1);
			GridCell right = maze.getCell(x, y + 1);
			if(maze.isValidMove(up) && up.getDistance() < distance)
				stack.push(up);
			else if(maze.isValidMove(down) && down.getDistance() < distance)
				stack.push(down);
			else if(maze.isValidMove(left) && left.getDistance() < distance)
				stack.push(left);
			else if(maze.isValidMove(right) && right.getDistance() < distance)
				stack.push(right);
			distance--;
		}
		while(!stack.isEmpty()) {
			maze.markMove(stack.pop());
		}
		return true;
	}
	
	public void reset(){
		stack.makeEmpty();
		queue.makeEmpty();
	}
	
	public static void main(String[] args){
		MazeSolver testDriver = new MazeSolver(25);
	}
}
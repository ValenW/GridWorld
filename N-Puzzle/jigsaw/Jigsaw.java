package jigsaw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

/** 在此类中填充算法，完成重拼图游戏（N-数码问题）
 * @author abe
 *
 */
public class Jigsaw {
	JigsawNode beginJNode;		// 拼图的起始状态节点
	JigsawNode endJNode;		// 拼图的目标状态节点
	JigsawNode currentJNode;	// 拼图的当前状态节点
	private Vector<JigsawNode> openList;	// open表 ：用以保存已发现但未访问的节点
	private Vector<JigsawNode> closeList;	// close表：用以保存已访问的节点
	private Vector<JigsawNode> solutionPath;// 解路径  ：用以保存从起始状态到达目标状态的移动路径中的每一个状态节点
	private boolean isCompleted;	// 完成标记：初始为false;当求解成功时，将该标记至为true
	private int searchedNodesNum;	// 已访问节点数： 用以记录所有访问过的节点的数量

	JigsawNode backingJNode;
	private Vector<JigsawNode> backOpenList;
	private Vector<JigsawNode> backCloseList;

	/**拼图构造函数
	 * @param bNode - 初始状态节点
	 * @param eNode - 目标状态节点
	 */
	public Jigsaw(JigsawNode bNode, JigsawNode eNode) {
		this.beginJNode = new JigsawNode(bNode);
		this.endJNode = new JigsawNode(eNode);
		this.currentJNode = new JigsawNode(bNode);
		this.backingJNode = new JigsawNode(eNode);
		this.openList = new Vector<JigsawNode>();
		this.closeList = new Vector<JigsawNode>();
		this.backOpenList = new Vector<JigsawNode>();
		this.backCloseList = new Vector<JigsawNode>();
		this.solutionPath = null;
		this.isCompleted = false;
		this.searchedNodesNum = 0;
	}

	/**此函数用于打散拼图：将输入的初始状态节点jNode随机移动len步，返回其打散后的状态节点
	 * @param jNode - 初始状态节点
	 * @param len - 随机移动的步数
	 * @return 打散后的状态节点
	 */
	public static JigsawNode scatter(JigsawNode jNode, int len) {
		int randomDirection;
		len += (int) (Math.random() * 2);
		JigsawNode jigsawNode = new JigsawNode(jNode);
		for (int t = 0; t < len; t++) {
			int[] movable = jigsawNode.canMove();
			do{randomDirection = (int) (Math.random() * 4);}
			while(0 == movable[randomDirection]);
			jigsawNode.move(randomDirection);
		}
		jigsawNode.setInitial();
		return jigsawNode;
	}

	/**获取拼图的当前状态节点
	 * @return currentJNode -  拼图的当前状态节点
	 */
	public JigsawNode getCurrentJNode() {
		return currentJNode;
	}

	/**设置拼图的初始状态节点
	 * @param jNode - 拼图的初始状态节点
	 */
	public void setBeginJNode(JigsawNode jNode) {
		beginJNode = jNode;
	}

	/**获取拼图的初始状态节点
	 * @return beginJNode - 拼图的初始状态节点
	 */
	public JigsawNode getBeginJNode() {
		return beginJNode;
	}

	/**设置拼图的目标状态节点
	 * @param jNode - 拼图的目标状态节点
	 */
	public void setEndJNode(JigsawNode jNode) {
		this.endJNode = jNode;
	}

	/**获取拼图的目标状态节点
	 * @return endJNode - 拼图的目标状态节点
	 */
	public JigsawNode getEndJNode() {
		return endJNode;
	}

	/**获取拼图的求解状态
	 * @return isCompleted - 拼图已解为true；拼图未解为false
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**计算解的路劲
	 * @return 若有解，则将结果保存在solutionPath中，返回true; 若无解，则返回false
	 */
	private boolean calSolutionPath() {
		if (!this.isCompleted()) {
			return false;
		} else {
			JigsawNode jNode = this.currentJNode;
			solutionPath = new Vector<JigsawNode>();
			while (jNode != null) {
				solutionPath.addElement(jNode);
				jNode = jNode.getParent();
			}
			jNode = this.backingJNode;
			while (jNode != null) {
				solutionPath.insertElementAt(jNode, 0);
				jNode = jNode.getParent();
			}
			return true;
		}
	}

	/**获取解路径文本
	 * @return 解路径solutionPath的字符串，若有解，则分行记录从初始状态到达目标状态的移动路径中的每一个状态节点；
	 * 若未解或无解，则返回提示信息。
	 */
	public String getSolutionPath() {
		String str = new String();
		str += "Begin->";
		if (this.isCompleted) {
			if (solutionPath.elementAt(0).equals(this.endJNode)) {
				for (int i = solutionPath.size() - 1; i >= 0; i--) {
					str += solutionPath.elementAt(i).toString() + "->";
				}
				str+="End";
			} else {
				for (int i = 0; i < solutionPath.size(); i++) {
					str += solutionPath.elementAt(i).toString() + "->";
				}
				str+="End";
			}
		} else
			str = "Jigsaw Not Completed.";
		return str;
	}

	/**获取访问过的节点数searchedNodesNum
	 * @return 返回所有已访问过的节点总数
	 */
	public int getSearchedNodesNum() {
		return searchedNodesNum;
	}
	
	/**将搜索结果写入文件中，同时显示在控制台
	 * 若搜索失败，则提示问题无解，输出已访问节点数；
	 * 若搜索成功，则输出初始状态beginJnode，目标状态endJNode，已访问节点数searchedNodesNum，路径深度nodeDepth和解路径solutionPath。
	 * @param pw - 文件输出PrintWriter类对象，如果pw为null，则写入到D://Result.txt
	 * @throws IOException
	 */
	public void printResult(PrintWriter pw) throws IOException{
		boolean flag = false;
		if(pw == null){
			pw = new PrintWriter(new FileWriter("Result.txt"));// 将搜索过程写入D://BFSearchDialog.txt
			flag = true;
		}
		if (this.isCompleted == true) {
			// 写入文件
			pw.println("Jigsaw Completed");
			pw.println("Begin state:" + this.getBeginJNode().toString());
			pw.println("End state:" + this.getEndJNode().toString());
			pw.println("Solution Path: ");
			pw.println(this.getSolutionPath());
			pw.println("Total number of searched nodes:" + this.getSearchedNodesNum());
			pw.println("Length of the solution path is:" + this.getCurrentJNode().getNodeDepth());

			
			// 输出到控制台
			System.out.println("Jigsaw Completed");
			System.out.println("Begin state:" + this.getBeginJNode().toString());
			System.out.println("End state:" + this.getEndJNode().toString());
			System.out.println("Solution Path: ");
			System.out.println(this.getSolutionPath());
			System.out.println("Total number of searched nodes:" + this.getSearchedNodesNum());
			System.out.println("Length of the solution path is:" + this.getCurrentJNode().getNodeDepth());

			
		} 
		else {
			// 写入文件
			pw.println("No solution. Jigsaw Not Completed");
			pw.println("Begin state:" + this.getBeginJNode().toString());
			pw.println("End state:" + this.getEndJNode().toString());
			pw.println("Total number of searched nodes:"
					+ this.getSearchedNodesNum());
			
			// 输出到控制台
			System.out.println("No solution. Jigsaw Not Completed");
			System.out.println("Begin state:" + this.getBeginJNode().toString());
			System.out.println("End state:" + this.getEndJNode().toString());
			System.out.println("Total number of searched nodes:"
					+ this.getSearchedNodesNum());
		}
		if(flag)
			pw.close();
	}

	/**探索所有与jNode邻接(上、下、左、右)且未曾被访问的节点
	 * @param jNode - 要探索的节点
	 * @return 包含所有与jNode邻接且未曾被访问的节点的Vector<JigsawNode>对象
	 */
	private Vector<JigsawNode> findFollowJNodes(JigsawNode jNode, Vector<JigsawNode> closeList, Vector<JigsawNode> openList) {
		Vector<JigsawNode> followJNodes = new Vector<JigsawNode>();
		JigsawNode tempJNode;
		for(int i=0; i<4; i++){
			tempJNode = new JigsawNode(jNode);
			if(tempJNode.move(i) && !closeList.contains(tempJNode) && !openList.contains(tempJNode))
				followJNodes.addElement(tempJNode);
		}
		return followJNodes;
	}

	/**排序插入openList：按照节点的代价估值（estimatedValue）将节点插入openList中，估值小的靠前。
	 * @param jNode - 要插入的状态节点
	 */
	private void sortedInsertOpenList(JigsawNode jNode, Vector<JigsawNode> topenList) {
		if (topenList.equals(backOpenList)) {
			topenList.addElement(jNode);
			return;
		}
		this.estimateValue(jNode);
		for (int i = 0; i < topenList.size(); i++) {
			if (jNode.getEstimatedValue() < topenList.elementAt(i)
					.getEstimatedValue()) {
				topenList.insertElementAt(jNode, i);
				return;
			}
		}
		topenList.addElement(jNode);
	}
	
	
	
	// ****************************************************************
	// *************************实验任务************************
	/**实验任务一：广度优先搜索算法，求指定3*3拼图（8-数码问题）的最优解
	 * 要求：填充广度优先搜索算法BFSearch()，执行测试脚本RunnerPart1
	 * 主要涉及函数：BFSearch()
	 */
	/**实验任务二：启发式搜索算法，求解随机5*5拼图（24-数码问题）
	 * 要求：1.修改启发式搜索算法ASearch()和代价估计函数estimateValue()，执行测试脚本RunnerPart2
	 *      2.访问节点总数不超过25000个
	 * 主要涉及函数：ASearch()，estimateValue()
	 */
	// ****************************************************************
	
	/**（实验一）广度优先搜索算法，求解指定3*3拼图（8-数码问题）的最优解。
	 * 要求函数结束后：1,isCompleted记录了求解完成状态；
	 *              2,closeList记录了所有访问过的节点；
	 *     		    3,searchedNodesNum记录了访问过的节点数；
	 *              4,solutionPath记录了解路径。
	 * @return isCompleted, 搜索成功时为true,失败为false
	 * @throws IOException
	 */
	public boolean BFSearch() throws IOException {
		// 将搜索过程写入D://BFSearchDialog.txt
		String filePath = "BFSearchDialog.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		// *************************************

		// Write your code here.
		openList.add(beginJNode);
		while (!openList.isEmpty()) {
			currentJNode = openList.remove(0);
			closeList.add(currentJNode);
			searchedNodesNum++;

			// 记录并显示搜索过程
			pw.println("Searching.....Number of searched nodes:" + this.closeList.size() + "   Current state:" + this.currentJNode.toString());
			System.out.println("Searching.....Number of searched nodes:" + this.closeList.size() + "   Current state:" + this.currentJNode.toString());
			// ********************************

			if (currentJNode.equals(endJNode)) {
				isCompleted = true;
				calSolutionPath();
				searchedNodesNum = closeList.size();
				break;
			}
			Vector<JigsawNode> folNodes = findFollowJNodes(currentJNode, this.closeList, this.openList);
			for (JigsawNode n : folNodes) {
				openList.add(n);
			}
		}
		
		// *************************************
		this.printResult(pw);
		pw.close();
		// System.out.println("Record into " + filePath);
		return isCompleted;
	}
	
	/**（Demo+实验二）启发式搜索。访问节点数大于30000个则认为搜索失败。
	 * 函数结束后：isCompleted记录了求解完成状态；
	 *           closeList记录了所有访问过的节点；
	 *           searchedNodesNum记录了访问过的节点数；
	 *           solutionPath记录了解路径。
	 *  搜索过程和结果会记录在D://DemoASearchDialog.txt中。
	 * @return 搜索成功返回true,失败返回false
	 * @throws IOException
	 */
	public boolean ASearch() throws IOException{
		// 将搜索过程写入ASearchDialog.txt
		String filePath = "ASearchDialog.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		
		// 访问节点数大于30000个则认为搜索失败
		int maxNodesNum = 30000;  
		
		// 用以存放某一节点的邻接节点
		
		// 重置求解完成标记为false
		isCompleted = false;           
		
		// (1)将起始节点放入openList中
		this.sortedInsertOpenList(this.beginJNode, openList);
		backOpenList.add(endJNode);
		// (2) 如果openList为空，或者访问节点数大于maxNodesNum个，则搜索失败，问题无解;否则循环直到求解成功
		while (this.openList.isEmpty() != true && searchedNodesNum <= maxNodesNum) {
			if (opereJNode(currentJNode, openList, closeList, backOpenList, pw)) {
				break;
			}
		}
		this.printResult(pw);	// 记录搜索结果
		pw.close(); 			// 关闭输出文件
		// System.out.println("Record into " + filePath);
		return isCompleted;
	}
	
	/**（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)=s(n)。
	 * s(n)代表后续节点不正确的数码个数
	 * @param jNode - 要计算代价估计值的节点；此函数会改变该节点的estimatedValue属性值。
	 */
	private void estimateValue(JigsawNode jNode) {
		if (jNode.getNodeDepth() >= 900) {
			jNode.setEstimatedValue(30000);
			return;
		}
		int dimension = JigsawNode.getDimension();

		int wrongFollowNum = 0; // 后续节点不正确的数码个数
		for(int index = 1; index < dimension * dimension; index++) {
			if (jNode.getNodesState()[index] == 0) {
				continue;
			}
 			if(jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
				wrongFollowNum++;
 			}
		}

		double distence = 0.0;
		int mDistence = 0; // distence
		for(int index = 1; index <= dimension * dimension; index++) {
			if (jNode.getNodesState()[index] == 0) continue;
			int d1x = (index - 1) % dimension;
			int d1y = (index - 1) / dimension;
			int d2x = (jNode.getNodesState()[index] - 1) % dimension;
			int d2y = (jNode.getNodesState()[index] - 1) / dimension;
			int x = Math.abs(d1x - d2x);
			int y = Math.abs(d1y - d2y);
			mDistence += x + y;
			distence += Math.sqrt(x * x + y * y);
			// System.out.println("x: " + x + " y:" + y + "\nmDist: " + jNode.getNodesState()[index] + " in " + index + " is: " + (int)(x + y) + "\ndist is: " + Math.sqrt(x * x + y * y) + "\n");
		}

		int wrongNum = 0; // 不正确的数码个数
		for(int index = 1; index < dimension * dimension; index++) {
			if(jNode.getNodesState()[index] != index) {
				wrongNum++;
			}
		}
		if (jNode.getNodesState()[dimension * dimension] != 0) {
			wrongNum++;
		}
		int first = mDistence * 10 + (int) (distence * 7) + wrongFollowNum * 5 + wrongNum;
		int second = mDistence * 10 + (int) (distence * 7) + wrongFollowNum * 50 + wrongNum * 5;
		if (getSearchedNodesNum() > 1500) {
			jNode.setEstimatedValue(first);
		} else {
			jNode.setEstimatedValue(second);
		}
	}

	public boolean TwoWayBFSearch() throws IOException {
		// 将搜索过程写入D://BFSearchDialog.txt
		String filePath = "TwoWayBFSearch.txt";
		PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		// *************************************

		// Write your code here.
		openList.add(beginJNode);
		backOpenList.add(endJNode);
		while (!openList.isEmpty() && !backOpenList.isEmpty()) {
			if (opereJNode(currentJNode, openList, closeList, backOpenList, pw)
				|| opereJNode(backingJNode, backOpenList, backCloseList, openList, pw)) {
				break;
			}
		}
		
		// *************************************
		this.printResult(pw);
		pw.close();
		//System.out.println("Record into " + filePath);
		return isCompleted;
	}

	public boolean opereJNode(JigsawNode tcurrentJNode, Vector<JigsawNode> topenList, Vector<JigsawNode> tcloseList, Vector<JigsawNode> tendList, PrintWriter tpw) {
		tcurrentJNode = topenList.remove(0);
		tcloseList.add(tcurrentJNode);
		searchedNodesNum++;
		/* 记录并显示搜索过程 */
		tpw.println("Searching.....Number of searched nodes:" + tcloseList.size() + "   Current state:" + tcurrentJNode.toString());
		System.out.println("Searching.....Number of searched nodes:" + tcloseList.size() + "   Current state:" + tcurrentJNode.toString());
		/* *******************************/

		Vector<JigsawNode> folNodes = findFollowJNodes(tcurrentJNode, tcloseList, topenList);
		for (JigsawNode n : folNodes) {
			if (tendList.contains(n)) {
				isCompleted = true;
				this.currentJNode = tcurrentJNode;
				for (int i = 1; i <= tendList.size(); i++) {
					if (tendList.get(tendList.size() - i).equals(n)) {
						this.backingJNode = tendList.get(tendList.size() - i);
					}
				}
				calSolutionPath();
				searchedNodesNum++;
				return true;
			}
			this.sortedInsertOpenList(n, topenList);
		}
		return false;
	}
}

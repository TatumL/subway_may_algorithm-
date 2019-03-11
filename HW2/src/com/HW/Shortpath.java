
package com.HW;

/**         
 * @Algorithm_Description:
 * How to go next node(station)? Is it Ans?
 * 
 * It's Ans will stop loop. Not jump to 1.
 * 
 * 1. Do you have only one choice for the next step? If yes, go (mandatory) or
 * skip to 2.
 * 
 * 2. Is there Ans exists in the next step? Yes go (force) not jump to 3.0 .
 * 
 * 3.0 When there are many stations to choose from to assess whether to transfer.
 * 
 * 3.0.1 If you do not transfer(in the same line) to calculate the distance 
 * that is next_station to Ans.
 * 
 * 3.0.2 If you want to transfer to calculate the distance that is next_station to
 * Ans, the calculation method is to find the shortest distance each station(not on same line) to Ans 
 *  
 * 3.0.3 Compare the results from 3.0.1 & 3.0.2 to find the best and output back to 1.
 * 
 *  Edited by 2018/04/25 
 **/
import java.util.Scanner;
import java.util.*;

public class Shortpath {

	public static int max = 10000;
	private static Scanner sc;

	/** 前處理&輸入 **/
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();
	    /** 
	     * @param n is start 
	     * @param m is number of stations 
	     * @param line_arr[][] matrix is saving all of line's contains stations
	     * @param node_info[][], This matrix is saving saving all of stations and each station's contains line
	     * @param x[], It is to help node_info[][] that build a length of rows
	     * @return 
	     * @Author: Tatum   
	     * @Description: Shortest subway path from start to finish with the minimum transfers
	     */
		n++;
		m++;
		int line_arr[][]; 
		int node_info[][]; 
		int x[] = new int[m]; 
		String strMain = null;
		line_arr = new int[n][];
		node_info = new int[m][]; // That both is to build a length of rows
		for (int i = 0; i < n; i++) { // input create the each line contains of stations.
			strMain = sc.nextLine();
			String[] arrSplit = strMain.split(" "); // That is using the String matrix to input data because Scanner
													// does not know that the user has completed entering inputs.
			line_arr[i] = new int[arrSplit.length];
			for (int j = 0; j < arrSplit.length; j++) {
				try {
					int ind = Integer.parseInt(arrSplit[j]); // handling the data to line_arr
					line_arr[i][j] = ind;
					x[ind]++;
				} catch (NumberFormatException ex) { // handle your exception
				}
			}
		}
		for (int i = 0; i < m; i++) {
			node_info[i] = new int[x[i]]; // This is creating node_info[i]'s length of columns.
		}
		int u[] = new int[m]; // Matrix u[] and this loop is to create dynamic two dimensional array
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < line_arr[i].length; j++) {
				try {
					node_info[line_arr[i][j]][u[line_arr[i][j]]] = i;
					u[line_arr[i][j]]++;
				} catch (NumberFormatException ex) { // handle your exception
				}
			}
		}

		int a = 0, b;
		float c;
		float adj_arr[][] = new float[m][m];
		while (a != -1) { //
			a = sc.nextInt();
			if (a == -1)
				break;
			b = sc.nextInt();
			c = sc.nextFloat();
			adj_arr[a][b] = c;
			adj_arr[b][a] = c;
		}
		/** 查詢路線 **/
		int abc = 1; // That "abc" is used to make a Infinite loop.
		while (abc == 1) {
			float cp_arr[][] = new float[m][m];
			int start, Ans;
			int yes = 1;
			start = sc.nextInt();
			Ans = sc.nextInt();
			System.out.print("" + start); // Print the first Station

			// **************//
			for (int i = 0; i < m; i++)
				for (int j = 0; j < m; j++)
					cp_arr[i][j] = adj_arr[i][j]; // pre-work
			for (int i = 0; i < m; i++) { // delet
				int cont = 0, tmp_a = 0, tmp_b = 0;
				for (int j = 0; j < m; j++) {
					if (i == start || i == Ans)
						break;
					if (cp_arr[i][j] > 0.0f) {
						cont++;
						tmp_a = i;
						tmp_b = j;
						yes = 0;
					}
					if (cont > 1.0)
						break;
					else
						yes = 0;
				}
				if (cont == 1) {
					cp_arr[tmp_a][tmp_b] = 0;
					cp_arr[tmp_b][tmp_a] = 0;
				}
			}
			// ********************//
			int node;
			int line = -1;
			int trans = 0, step = 0;
			node = start;
			// ****************//

			while (yes != 4) {
				strMain = "";
				int cont = 0;
				int tep_n = 0;
				for (int i = 1; i < m; i++) {
					if (cp_arr[node][i] > 0.0f) {
						cont++;
						tep_n = i;
						strMain += i + " ";
					}
				}
				String[] arrSplit = strMain.split(" ");
				int nodelist[] = new int[arrSplit.length];
				for (int i = 0; i < arrSplit.length; i++) {
					try {
						int ind = Integer.parseInt(arrSplit[i]);
						nodelist[i] = ind;
					} catch (NumberFormatException ex) { // handle your exception
					}
				}

				for (int i = 0; i < nodelist.length; i++)
					if (nodelist[i] == Ans) { // 1.
						if (line != sameline(node, Ans, node_info)) {
							line = sameline(node, Ans, node_info);
							trans++;
						} else
							line = sameline(node, Ans, node_info);
						System.out.print(" (Line " + line + ") " + Ans);
						cont = 0;
						yes = 4;
						System.out.println();
					}
				if (cont == 1) { // 2. 補上轉乘?

					for (int i = 0; i < node_info[node].length; i++) {
						for (int j = 0; j < node_info[tep_n].length; j++)
							if (node_info[node][i] == node_info[tep_n][j]) {
								if (line != node_info[tep_n][j])
									trans++;
								line = node_info[node][i];
								break;
							}
					}
					System.out.print(" (Line " + line + ") " + tep_n);
					cont = 0;
					cp_arr[node][tep_n] = 0.0f;
					cp_arr[tep_n][node] = 0.0f; // 刪除
					node = tep_n;
				}

				if (cont > 1) { // 3. 往下檢查
					int tmp_line = line;
					int tmp1_trans = trans, tmp2_trans = trans;
					int e_node_res = nodelist[0], e_line_res = max, e_line = node_info[nodelist[0]][0]; // result
																										// 會放最小的值，step
																										// 會放各種情況的結果
																										// trans 會放轉乘數
					int this_line = 0;
					for (int i = 0; i < nodelist.length; i++) { // 下一步中 nodelist 各node
						int this_line_best = max, this_node = nodelist[i];
						int res = max, step1 = max, Ans_line1 = 0;
						int res2 = max, step2 = max - 1, Ans_line2 = 0;
						for (int j = 0; j < node_info[nodelist[i]].length; j++) { // 各line
							this_line = node_info[nodelist[i]][j];
							int Ans_line = 0;
							tmp1_trans = trans;// init
							res = max;
							res2 = max;
							for (int k = 0; k < node_info[Ans].length; k++) { // 是否有跟Ans 同一條line上的結果為和?
								if (this_line == node_info[Ans][k])
									step1 = slps(this_node, this_line, Ans, node_info[Ans][k], line_arr);
								if (res > step1) {
									res = step1;
									Ans_line1 = node_info[Ans][k];
								} // 存下跟 Ans同line的值
							}

							for (int k = 0; k < node_info[Ans].length; k++) {
								if (this_line != node_info[Ans][k]) {
									step2 = crossline(this_node, this_line, Ans, node_info[Ans][k], line_arr,
											node_info);
									if (res2 > step2) {
										res2 = step2;
										Ans_line2 = node_info[Ans][k];
									}
								} // 存不同line的值
									// 期望結果是 this_node，step2 Ans_line2 找到最好的。
							}
							if (res < res2)
								this_line_best = res;
							else if (res > res2) {
								this_line_best = res2;
								tmp1_trans++;
							}
							if (res == res2 && res != max)
								this_line_best = res;
							if (res == max && res2 == max)
								this_line_best = max;
							if (e_line_res >= this_line_best) {
								if (e_line_res == this_line_best && tmp1_trans == trans) {
									e_line_res = this_line_best;
									e_node_res = this_node;
									e_line = this_line;
								} else {
									e_line_res = this_line_best;
									e_node_res = this_node;
									e_line = this_line;
								}
							}
						} //

					}
					// line
					for (int i = 0; i < node_info[node].length; i++) {
						for (int j = 0; j < node_info[e_node_res].length; j++)
							if (node_info[node][i] == node_info[e_node_res][j]) {
								line = node_info[node][i];
								break;
							}
					}
					System.out.print(" (Line " + line + ") " + e_node_res);
					cont = 0;
					cp_arr[node][e_node_res] = 0.0f;
					cp_arr[e_node_res][node] = 0.0f; // 刪除
					node = e_node_res;

				}
				
			}

		}
	}

	static int slps(int this_node, int this_line, int Ans, int this_Ans_line, int line_arr[][]) { // 有出現在同一條線上直接算距離
		int x1 = 0, x2 = 0;
		int result = max;
		for (int i = 0; i < line_arr[this_Ans_line].length; i++) {
			if (line_arr[this_Ans_line][i] == Ans)
				x1 = i;
			if (line_arr[this_Ans_line][i] == this_node)
				x2 = i;
			if (x1 != 0 && x2 != 0)
				result = java.lang.Math.abs(x2 - x1);
		}
		return result;
	}

	static int sameline(int node_a, int node_b, int node_info[][]) { // 找到這兩條線上有相同的線
		int line = 0;
		for (int i = 0; i < node_info[node_b].length; i++) // 找到相同的線
			for (int j = 0; j < node_info[node_a].length; j++) {
				if (node_info[node_b][i] == node_info[node_a][j]) {
					return node_info[node_a][j];
				}
			}
		return line;
	}

	// 找到 交叉點 並且回傳 是那一點最好
	static int crossline(int this_node, int this_line, int Ans, int this_Ans_line, int line_arr[][],
			int node_info[][]) {
		int result = max;
		int step = max;
		int cross_node = 0;
		int use = max;

		for (int i = 0; i < node_info[this_node].length; i++)
			for (int j = 0; j < line_arr[this_line].length; j++) {
				result = slps(line_arr[this_line][j], this_line, Ans, this_Ans_line, line_arr);
				if (step > result && result != 0) {
					step = result;
					cross_node = line_arr[this_line][j];
					use = step;
				} // 紀錄最佳的點跟距離

			}
		if (cross_node != 0) {
			result = slps(result = cross_node, this_line, this_node, this_line, line_arr);// 計算 this node跟交叉點的距離
			step = step + result; // 加總起來 this_node to 交叉點+ 交叉點to Ans
			use = step;
		}
		return use;
	}
}

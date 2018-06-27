package source_code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TopToDown {
	//�õ��ѱ�ʹ�õ��ַ�֮����ַ�����������
	public List<Character> usetochange (List<Character> afterRemoveAllLeftRecursionRightpart){
		List<Character> list = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
		list.add((char) (65+i));
		}
		for(int i = 0 ; i < list.size();i++) {
			for(char t:afterRemoveAllLeftRecursionRightpart) {
				if(list.get(i).equals(t)) {
					list.remove(i);
				}
			}
		}
		return list;
	}

	public List<String> ExtractionLeftFactor(List<String> mat, List<Character> Leftpart) {
		List<String> myregList = new ArrayList<String>();
		String myfirstString = new String();
		int regk = 0; //����ָ��ڼ����ַ��������ָ�ַ�
		List<Integer> myintlist = new ArrayList<Integer>();//��¼�Ǽ����ķ������˸���
		// ����list��ÿһ����
		for (int i = 0; i < mat.size(); i++) {
			for (int j = i; j < mat.size(); j++) {
				// ����Ҳ���һ���ַ�����������
				if (mat.get(i).charAt(0) == mat.get(j).charAt(0) && mat.get(i).charAt(2) == mat.get(j).charAt(2)
						&& i != j) {
					myintlist.add(i);
					myintlist.add(j);
					// ����ÿһ���ַ�������ַ���ȣ����ַ�������
					for (int k = 0; k < mat.get(i).length(); k++) {
						for (int l = k; l < mat.get(j).length(); l++) {
							if (mat.get(i).charAt(k) == mat.get(j).charAt(l) && k == l) {
								myfirstString += mat.get(i).charAt(k); // ����ȵ��ַ�����string
								regk = k + 1;
							}
						}
					}
					if (mat.get(i).equals(myfirstString)) {//���һ���ķ�������һ���ķ��������Ƴ�#���Ƴ�ʣ�಻��ȵķ���
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + '#');
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(j).substring(regk));

					} else if (mat.get(j).equals(myfirstString)) {
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + '#');
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(i).substring(regk));
					} else {//��������ķ�û�а�����ϵ
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(i).substring(regk));
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(j).substring(regk));
					}

					myfirstString += usetochange(Leftpart).get(i);
					myregList.add(myfirstString);
					myfirstString = "";

				} else {
					myregList.add(mat.get(j));
				}
			}
		}
		for (int i = 0; i < myregList.size(); i++) {
			for (int j = 0; j < myintlist.size(); j++) {
				if (myregList.get(i).equals(mat.get(myintlist.get(j)))) {
					myregList.remove(i);
				}
			}
		}
		return myregList;
	}

	public List<String> RemoveLeftRecursion(List<String> mat) {// ����ֱ������ݹ麯��
		List<String> after = new ArrayList<String>();
		List<String> myreg = new ArrayList<String>();
		char splitcharacter = ':';
		for (int i = 0; i < mat.size(); i++) {
			if (mat.get(i).charAt(2) == mat.get(i).charAt(0)) {
				// ���� E��E+T �� ת��Ϊ e:+Te
				String a = "";
				a += dic(mat.get(i).charAt(0)); // ����ת������
				a += splitcharacter; // ��
				a += mat.get(i).substring(3); // ��ȡ��ݹ�֮���string
				a += dic(mat.get(i).charAt(0)); // ����ת������
				after.add(a);

				String b = "";
				b += dic(mat.get(i).charAt(0));
				b += splitcharacter;
				b += '#';
				after.add(b);

				myreg.add(mat.get(i));// �Ĵ�һ������,���������ж��Ƿ�Ҫ��Ӽ���

				for (int j = 0; j < mat.size(); j++) {
					// ����E��T ��T������E��E == E ʱ
					if (mat.get(i).charAt(0) != mat.get(j).charAt(2) && mat.get(j).charAt(0) == mat.get(i).charAt(0)) {
						// ���� E:T ת��Ϊ E��Te
						after.add(mat.get(j) + dic(mat.get(i).charAt(0)));
						myreg.add(mat.get(j));
					}
				}

			}
		}
		for (int i = 0; i < mat.size(); i++) {
			for (int j = 0; j < myreg.size(); j++) {
				if (mat.get(i).equals(myreg.get(j))) {
					mat.remove(i);
				}
			}
		}
		for (int i = 0; i < mat.size(); i++) {
			after.add(mat.get(i));
		}
		after = new ArrayList<String>(new HashSet<String>(after));
		return after;
	}
	//����������ݹ�
	public List<String> RemoveAllRecursion(List<String> mat) {
		List<String> afterall = new ArrayList<String>();
		List<String> mynob = new ArrayList<String>();

		for (int i = 0; i < mat.size(); i++) {
			for (int j = 0; j < mat.size(); j++) {
				if (mat.get(i).charAt(2) == mat.get(j).charAt(0)) {
					String a = "";
					a += mat.get(i).charAt(2);
					afterall.add(mat.get(i).replaceAll(a, mat.get(j).substring(2)));
					mynob.add(mat.get(i));
				}
			}
		}
		mynob = new ArrayList<String>(new HashSet<String>(mynob));
		for (int i = 0; i < mat.size(); i++) {
			for (int j = 0; j < mynob.size(); j++) {
				if (mat.get(i).equals(mynob.get(j))) {
					mat.remove(i);
				}
			}
		}
		for (String t : mat) {
			afterall.add(t);
		}
		for (int i = 0; i < afterall.size(); i++) {
			for (int j = 0; j < afterall.get(i).length(); j++) {
				if (afterall.get(i).charAt(j) == '#') {
					if (j + 1 < afterall.get(i).length()) {
						String a = "";
						a += afterall.get(i).substring(0, j);
						a += afterall.get(i).substring(j + 1);
						afterall.remove(i);
						afterall.add(a);
					}
				}
			}
		}
		return afterall;
	}
	public char dic(char A) {// ת������,����Eת����e
		return (char) (A ^ ' ');
	}

	int myPointer = 0, myerrorreg = 0;//��¼ƥ�䵽��һ���ַ�����¼������Ϣ

	public void E(String myInputString) {
		if (myInputString.charAt(myPointer) == 'i') {
			System.out.println("E->ite");
			myPointer++;
			t(myInputString);//
			e(myInputString);
		} else {
			if (myInputString.charAt(myPointer) == '(') {
				System.out.println("E->(E)te");
				myPointer++;
				E(myInputString);
				if (myInputString.charAt(myPointer) == ')') {//
					myPointer++;
					t(myInputString);
					e(myInputString);
				} else if (myInputString.charAt(myPointer) != '$') {
					myerrorreg++;
				}
			}
		}
	}

	public void T(String myInputString) {
		if (myInputString.charAt(myPointer) == 'i') {
			System.out.println("T->it");
			myPointer++;
			t(myInputString);
		} else {
			if (myInputString.charAt(myPointer) == '(') {
				System.out.println("T->(E)t");
				myPointer++;
				E(myInputString);
				if (myInputString.charAt(myPointer) == ')') {
					myPointer++;
					t(myInputString);
				} else {
					myerrorreg++;
				}
			}
		}
	}

	public void F(String myInputString) {
		if (myInputString.charAt(myPointer) == '(') {
			System.out.println("F->(E)");
			myPointer++;
			E(myInputString);
			if (myInputString.charAt(myPointer) == ')') {
				myPointer++;
			} else {
				myerrorreg++;
			}
		} else if (myInputString.charAt(myPointer) == 'i') {
			System.out.println("F->i");
			myPointer++;
			// System.out.println(myPointer);
		} else {
			myerrorreg++;
		}
	}

	public void t(String myInputString) {
		if (myInputString.charAt(myPointer) == '*') {
			System.out.println("t->*Ft");
			myPointer++;
			F(myInputString);
			// System.out.println(myPointer);
			t(myInputString);
		} else if (myInputString.charAt(myPointer) == ')' || myInputString.charAt(myPointer) == '+'
				|| myInputString.charAt(myPointer) == '$') {
			System.out.println("t->#");
		} else {
			myerrorreg++;
		}
	}

	public void e(String myInputString) {
		if (myInputString.charAt(myPointer) == '+') {
			System.out.println("e->+Te");
			myPointer++;
			T(myInputString);
			e(myInputString);
		} else if (myInputString.charAt(myPointer) == ')' || myInputString.charAt(myPointer) == '$') {
			System.out.println("e->#");
		} else {
			myerrorreg++;
		}

	}

	public static void main(String[] args) throws IOException {
		TopToDown toptodown = new TopToDown();
		List<String> allline = new ArrayList<String>();// �ķ�
		List<Character> notfinallist = new ArrayList<Character>();// ���ս����
		List<Character> leftpart = new ArrayList<Character>();// �ķ���
		List<String> rightpart = new ArrayList<String>();// �ķ��Ҳ�
		List<String> afterRemoveDirectLeftRecursion = new ArrayList<String>();// �ķ�����ֱ����ݹ�
		List<String> regallline = new ArrayList<String>();// �Ĵ��ķ�
		List<String> afterRemoveAllLeftRecursion = new ArrayList<String>();// �ķ���������
		List<Character> afterRemoveAllLeftRecursionLeftpart = new ArrayList<Character>(); // ������ݹ����ķ���
		List<String> afterRemoveAllLeftRecursionRightpart = new ArrayList<String>();// ������ݹ����ķ��Ҳ�
		List<String> testfile = new ArrayList<String>();

		String pathname = "E://tt.txt";
		File filename = new File(pathname);
		InputStreamReader read;
		read = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(read);
		String oneline = "";
		oneline = br.readLine();
		int firstoneline = Integer.parseInt(oneline);
		// allline ��ȡ�ķ�
		for (int i = 0; i < firstoneline; i++) {
			oneline = br.readLine();
			allline.add(oneline);
		}

		String myTestPath = "E://test.txt";
		File testfilename = new File(myTestPath);
		InputStreamReader testread;
		testread = new InputStreamReader(new FileInputStream(testfilename));
		BufferedReader testbr = new BufferedReader(testread);
		for (int i = 0; i < 5; i++) {
			String testline = "";
			testline = testbr.readLine();
			testfile.add(testline);
		}

		// �Ĵ�allline��ȡ���ķ�
		for (int i = 0; i < allline.size(); i++) {
			regallline.add(allline.get(i));
		}

		// ��ȡ�ķ�����
		for (int i = 0; i < allline.size(); i++) {
			leftpart.add(allline.get(i).charAt(0));
		}
		// ��ȡ�ķ����Ҳ�
		for (int i = 0; i < allline.size(); i++) {
			rightpart.add(allline.get(i).substring(2));

		}
		// �����ķ��ķ��ս����
		notfinallist = new ArrayList<Character>(new HashSet<Character>(leftpart));

		System.out.println("�ķ�");
		for (String t : allline) {
			System.out.println(t);
		}
		System.out.println("���ս����");
		for (char t : notfinallist) {
			System.out.println(t);
		}

		// ���ú�������ֱ����ݹ�
		System.out.println("����ֱ����ݹ�");
		afterRemoveDirectLeftRecursion = toptodown.RemoveLeftRecursion(regallline);
		for (String t : afterRemoveDirectLeftRecursion) {
			System.out.println(t);
		}

		System.out.println("����������ݹ�");
		afterRemoveAllLeftRecursion = toptodown.RemoveAllRecursion(afterRemoveDirectLeftRecursion);
		for (int i = 0; i < afterRemoveAllLeftRecursion.size(); i++) {
			for (int j = 0; j < afterRemoveAllLeftRecursion.size(); j++) {
				if (afterRemoveAllLeftRecursion.get(i).charAt(2) == afterRemoveAllLeftRecursion.get(j).charAt(0)) {
					afterRemoveAllLeftRecursion = toptodown.RemoveAllRecursion(afterRemoveAllLeftRecursion);
				}
			}
		}
		for (String t : afterRemoveAllLeftRecursion) {
			System.out.println(t);
		}			
		// �����󲿺��Ҳ�
		for (String t : afterRemoveAllLeftRecursion) {
			afterRemoveAllLeftRecursionLeftpart.add(t.charAt(0));
		}
		for (String t : afterRemoveAllLeftRecursion) {
			afterRemoveAllLeftRecursionRightpart.add(t.substring(2));
		}
		
		System.out.println("��ȡ�󹫹�����");
		List<String> aftermmm = new ArrayList<String>();
		aftermmm = toptodown.ExtractionLeftFactor(afterRemoveAllLeftRecursion, afterRemoveAllLeftRecursionLeftpart);
		aftermmm = new ArrayList<String>(new HashSet<String>(aftermmm));
		for (String t : aftermmm) {
			System.out.println(t);
		}

		/*
		 * for (int i = 0; i < afterRemoveAllLeftRecursion.size(); i++) { for (int j =
		 * 2; j < afterRemoveAllLeftRecursion.get(i).substring(2).length(); j++) { for
		 * (int k = 0; k < afterRemoveAllLeftRecursionLeftpart.size(); k++) { if
		 * (afterRemoveAllLeftRecursion.get(i).charAt(j) ==
		 * afterRemoveAllLeftRecursionLeftpart.get(k)) {
		 * 
		 * } } } }
		 */
		// E(String myInputString, int myPointer, int myerrorreg)
		//
		System.out.println("���Ծ��ӣ�");
		System.out.println(testfile.get(2));
		System.out.println("���Խ����");
		toptodown.E(testfile.get(2));
		if (toptodown.myerrorreg > 0) {
			System.out.println("error");
		} else if (toptodown.myerrorreg == 0) {
			System.out.println("right");
		}

	}

}

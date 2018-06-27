package souce_code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ll1 {
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
				a += mat.get(i).substring(3); // ������ݹ�֮���string
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

	public List<String> RemoveAllRecursion(List<String> mat) {
		List<String> afterall = new ArrayList<String>();
		List<String> mynob = new ArrayList<String>();
		for (int i = 0; i < mat.size(); i++) {
			for (int j = 0; j < mat.size(); j++) {
				if (mat.get(i).charAt(2) == mat.get(j).charAt(0) && mat.get(j).charAt(2) == mat.get(i).charAt(0)) {// �Ҳ���һ���ַ������󲿵��ַ�
					String a = "";
					a += mat.get(i).charAt(2);
					afterall.add(mat.get(i).replaceAll(a, mat.get(j).substring(2)));// �����滻
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
		// ��û�в��빹��ļ���
		for (String t : mat) {
			afterall.add(t);
		}
		// ������E:#Hת��ΪE:H
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

	// �õ��ѱ�ʹ�õ��ַ�֮����ַ�����������
	public List<Character> usetochange(List<Character> afterRemoveAllLeftRecursionRightpart) {
		List<Character> list = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			list.add((char) (65 + i));
		}
		for (int i = 0; i < list.size(); i++) {
			for (char t : afterRemoveAllLeftRecursionRightpart) {
				if (list.get(i).equals(t)) {
					list.remove(i);
				}
			}
		}
		return list;
	}

	// ��ȡ�󹫹�����
	public List<String> ExtractionLeftFactor(List<String> mat, List<Character> Leftpart) {
		List<String> myregList = new ArrayList<String>();
		String myfirstString = new String();
		int regk = 0; // ����ָ��ڼ����ַ��������ָ��ķ�
		List<Integer> myintlist = new ArrayList<Integer>();// ��¼�Ǽ����ķ������˸���
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
					if (mat.get(i).equals(myfirstString)) {// ���һ���ķ�������һ���ķ��������Ƴ�#���Ƴ�ʣ�಻��ȵķ���
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + '#');
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(j).substring(regk));

					} else if (mat.get(j).equals(myfirstString)) {
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + '#');
						myregList.add(usetochange(Leftpart).get(i).toString() + ':' + mat.get(i).substring(regk));
					} else {// ��������ķ�û�а�����ϵ
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

	public char dic(char A) {// ת������,����Eת����e
		return (char) (A ^ ' ');
	}

	public String myFirstSet(char requiredValue, List<String> mat, List<Character> thisfinalpart) {// ���������ķ��������ս����
		String thisfirstset = "";
		for (int i = 0; i < mat.size(); i++) {
			for (char t : thisfinalpart) {
				if (mat.get(i).charAt(0) == requiredValue && mat.get(i).charAt(2) == t) {// ���ս�����Ҳ���һ������Ϊ�ս����
					thisfirstset += mat.get(i).charAt(2);
				} else if (mat.get(i).charAt(0) == requiredValue && mat.get(i).charAt(2) == '#') {// ����Ƴ���
					thisfirstset += '#';
				} else {
					for (int j = +1; j < mat.size(); j++) {
						if (mat.get(i).charAt(0) == requiredValue && mat.get(i).charAt(2) == mat.get(j).charAt(0)) {// �ݹ��ѯ
							thisfirstset = myFirstSet(mat.get(j).charAt(0), mat, thisfinalpart);
						}
					}
				}
			}
		}
		for (char t : thisfinalpart) {
			if (requiredValue == t) {// �ս���ŵ�first���ڱ���
				thisfirstset += t;
				return thisfirstset;
			}
		}
		thisfirstset = removeMethod(thisfirstset);
		return thisfirstset;

	}

	public String myFollowSet(char requiredValue, List<String> mat, List<Character> mynotfinalpart,
			List<Character> thisfinalpart, char mystartsign) {
		List<Character> myfollowset = new ArrayList<Character>();
		String thisfollowset = "";
		List<String> thisrightpart = new ArrayList<String>();
		for (int i = 0; i < mat.size(); i++) {// ��ȡ�Ҳ�
			thisrightpart.add(mat.get(i).substring(2));
		}
		if (requiredValue == mystartsign) {// ����ǿ�ʼ���������$
			thisfollowset += '$';
		}
		for (int i = 0; i < mat.size(); i++) {
			for (int j = 0; j < thisrightpart.get(i).length(); j++) {
				if (thisrightpart.get(i).charAt(j) == requiredValue) {// �ҵ��ַ�
					if (j + 1 < thisrightpart.get(i).length()) {// ����ַ����滹���ַ�
						for (char t : thisfinalpart) {
							if (thisrightpart.get(i).charAt(j + 1) == t) { // ������ַ����ս���������follow��
								myfollowset.add(t);
							} else {
								for (char k : mynotfinalpart) {
									if (thisrightpart.get(i).charAt(j + 1) == k) {// ������ַ��Ƿ��ս����
										String myfirstto = "";
										myfirstto = myFirstSet(k, mat, thisfinalpart);// �Ѹ÷��ŵ�first������
										for (int q = 0; q < myfirstto.length(); q++) {
											myfollowset.add(myfirstto.charAt(q));
										}
										for (String p : mat) {
											if (p.charAt(0) == k && p.charAt(2) == '#') {// ����÷��ս�������Ƴ�#,�Ѳ���ʽ�󲿷��ŵ�follow������
												String myfollowto = "";
												myfollowto = myFollowSet(p.charAt(0), mat, mynotfinalpart,
														thisfinalpart, mystartsign);
												for (int q = 0; q < myfollowto.length(); q++) {
													myfollowset.add(myfollowto.charAt(q));
												}
											}
										}
									}
								}
							}
						}
					} else if (j + 1 == thisrightpart.get(i).length()) { // ����û���ַ�
						if (mat.get(i).charAt(0) != requiredValue) { // ���������ַ������ڲ���ʽ���󲿣����󲿵�follow����
							String myfollowtoto = "";
							myfollowtoto = myFollowSet(mat.get(i).charAt(0), mat, mynotfinalpart, thisfinalpart,
									mystartsign);
							for (int q = 0; q < myfollowtoto.length(); q++) {
								myfollowset.add(myfollowtoto.charAt(q));
							}
						}
					}
				}
			}
		}
		myfollowset = new ArrayList<Character>(new HashSet<Character>(myfollowset));
		for (int i = 0; i < myfollowset.size(); i++) {
			if (myfollowset.get(i) == '#') {
				myfollowset.remove(i);
			}

		}
		for (char t : myfollowset) {
			thisfollowset += t;
		}

		return thisfollowset;
	}

	public String mySelect(List<String> mat, String onemat, List<Character> mynotfinalpart, List<Character> myfinalpart,
			char mystartsign) {
		String thisselectset = "";
		for (char a : mynotfinalpart) {
			if (onemat.charAt(2) == a) {// ����Ҳ���һ���Ƿ��ս����,��÷��ŵ�first
				thisselectset = myFirstSet(a, mat, myfinalpart);
			} else if (onemat.charAt(2) == '#') {// ����Ƴ��գ����󲿵�follow
				thisselectset = myFollowSet(onemat.charAt(0), mat, mynotfinalpart, myfinalpart, mystartsign);
			} else {
				for (char p : myfinalpart) {// ����Ҳ���һ�����ս���ţ������н����
					if (onemat.charAt(2) == p) {
						thisselectset += p;
					}
				}
			}
		}
		thisselectset = removeMethod(thisselectset);
		return thisselectset;
	}

	public void predictionAnalysis(Stack<Character> thisinput, Map<String, String> thisselectmap, char mystartsign) {
		Stack<Character> mystack = new Stack<Character>();
		mystack.push('$');
		mystack.push(mystartsign);
		while (mystack.peek() != '$') {// ���ջ��Ԫ�ز���$dc
			if (mystack.peek() == thisinput.peek()) { // �������ջ��ƥ��ջ��ջ��������������׳�
				mystack.pop();
				thisinput.pop();
			} else {
				for (String t : thisselectmap.keySet()) {
					for (int i = 0; i < thisselectmap.get(t).length(); i++) {
						if (thisinput.peek() == thisselectmap.get(t).charAt(i)) {// ��������ջ������ĳһselect��Ԫ��
							if (t.charAt(0) == mystack.peek()) {// ƥ��ջ��ջ�������ķ�����
								mystack.pop();
								for (int j = t.length() - 1; j >= 2; j--) {//�Ѳ���ʽ���Ҳ����ҵ���ѹ��
									mystack.push(t.charAt(j));
									if(t.charAt(j) == '#') {//�������ʽ�Ƴ��գ��׳�ƥ��ջ��ջ��
										mystack.pop();
									}
								}
								System.out.println(t);
							}
						}
					}
				}
			}
		}
	}

	public String removeMethod(String s) { // ȥ��
		StringBuffer sb = new StringBuffer();
		int len = s.length();
		int i = 0;
		boolean flag = false;
		for (i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (s.indexOf(c) != s.lastIndexOf(c)) {
				flag = false;
			} else {
				flag = true;
			}
			if (i == s.indexOf(c))
				flag = true;
			if (flag) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		ll1 myll1 = new ll1();
		List<String> allline = new ArrayList<String>();// �ķ�
		List<Character> leftpart = new ArrayList<Character>();// �ķ����ս����
		List<Character> finalpart = new ArrayList<Character>();// �ķ��ս����
		List<String> afterRemoveDirectLeftRecursion = new ArrayList<String>();// �ķ�����ֱ����ݹ�
		List<String> afterRemoveAllLeftRecursion = new ArrayList<String>();// �ķ�����������ݹ�
		List<Character> afterRemoveAllLeftRecursionLeftpart = new ArrayList<Character>(); // ����������ݹ����ķ����ս����
		List<String> afterAll = new ArrayList<String>(); // ��ȡ�󹫹����Ӻ���ķ�
		List<Character> afterAllLeftpart = new ArrayList<Character>(); // ��ȡ�󹫹����Ӻ���ķ����ս����
		Map<String, String> selectmap = new HashMap<String, String>();
		char startsign;// ��ʼ����
		// List<String> firstset = new ArrayList<String>();

		String pathname = "E://t3.txt";
		File filename = new File(pathname);
		InputStreamReader read;
		read = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(read);
		String oneline = "";
		oneline = br.readLine();
		int firstoneline = Integer.parseInt(oneline);
		startsign = br.readLine().charAt(0);
		// allline ��ȡ�ķ�
		for (int i = 0; i < firstoneline; i++) {
			oneline = br.readLine();
			allline.add(oneline);
		}
		int NumberOfFinal;
		NumberOfFinal = Integer.parseInt(br.readLine());
		for (int i = 0; i < NumberOfFinal; i++) {
			finalpart.add(br.readLine().charAt(0));
		}

		// ��ȡ�ķ�����
		for (int i = 0; i < allline.size(); i++) {
			leftpart.add(allline.get(i).charAt(0));
		}
		// �����ķ��ķ��ս����
		leftpart = new ArrayList<Character>(new HashSet<Character>(leftpart));

		System.out.println("�ķ�");
		for (String t : allline) {
			System.out.println(t);
		}
		System.out.println("��ʼ����");
		System.out.println(startsign);
		System.out.println("���ս����");
		for (char t : leftpart) {
			System.out.println(t);
		}

		System.out.println("�ս����");
		for (char t : finalpart) {
			System.out.println(t);
		}

		// ���ú�������ֱ����ݹ�
		System.out.println("����ֱ����ݹ�");
		afterRemoveDirectLeftRecursion = myll1.RemoveLeftRecursion(allline);
		for (String t : afterRemoveDirectLeftRecursion) {
			System.out.println(t);
		}

		System.out.println("����������ݹ�");
		afterRemoveAllLeftRecursion = myll1.RemoveAllRecursion(afterRemoveDirectLeftRecursion);
		for (int i = 0; i < afterRemoveAllLeftRecursion.size(); i++) {
			for (int j = 0; j < afterRemoveAllLeftRecursion.size(); j++) {
				if (afterRemoveAllLeftRecursion.get(i).charAt(2) == afterRemoveAllLeftRecursion.get(j).charAt(0)) {
					afterRemoveAllLeftRecursion = myll1.RemoveAllRecursion(afterRemoveAllLeftRecursion);
				}
			}
		}
		for (String t : afterRemoveAllLeftRecursion) {
			System.out.println(t);
		}
		// ������ս����
		for (String t : afterRemoveAllLeftRecursion) {
			afterRemoveAllLeftRecursionLeftpart.add(t.charAt(0));
		}
		afterRemoveAllLeftRecursionLeftpart = new ArrayList<Character>(
				new HashSet<Character>(afterRemoveAllLeftRecursionLeftpart));

		System.out.println("��ȡ�󹫹�����");
		afterAll = myll1.ExtractionLeftFactor(afterRemoveAllLeftRecursion, afterRemoveAllLeftRecursionLeftpart);
		afterAll = new ArrayList<String>(new HashSet<String>(afterAll));

		for (String t : afterAll) {
			System.out.println(t);
		}

		System.out.println("��ȡ�󹫹����Ӻ���ķ����ս����");
		for (String t : afterAll) {
			afterAllLeftpart.add(t.charAt(0));
		}
		afterAllLeftpart = new ArrayList<Character>(new HashSet<Character>(afterAllLeftpart));
		for (char t : afterAllLeftpart) {
			System.out.println(t);
		}

		String firstset = "";
		String followset = "";
		System.out.print("���ս����");
		System.out.print("	");
		System.out.print("first��");
		System.out.print("		");
		System.out.print("follow��");
		System.out.println();
		for (char t : afterAllLeftpart) {
			System.out.print(t);
			firstset = myll1.myFirstSet(t, afterAll, finalpart);
			System.out.print("		");
			System.out.print(firstset);
			System.out.print("		");
			followset = myll1.myFollowSet(t, afterAll, afterAllLeftpart, finalpart, startsign);
			System.out.print(followset);
			System.out.println();
		}

		String selectset = "";
		System.out.println();
		System.out.print("����ʽ");
		System.out.print("		");
		System.out.print("select��");
		System.out.println();
		for (String t : afterAll) {
			selectset = myll1.mySelect(afterAll, t, afterAllLeftpart, finalpart, startsign);
			selectmap.put(t, selectset);
		}
		for (String t : selectmap.keySet()) {
			System.out.print(t);
			System.out.print("		");
			System.out.print(selectmap.get(t));
			System.out.println();
		}
		// �ж��Ƿ�LL1
		List<String> selectreg = new ArrayList<String>();
		int isll1 = 0;
		for (int i = 0; i < afterAll.size(); i++) {
			for (int j = i + 1; j < afterAll.size(); j++) {
				if (afterAll.get(i).charAt(0) == afterAll.get(j).charAt(0)) {// �ж�ͬһ���ս�����Ƴ���select�Ƿ����
					selectreg.add(0, myll1.mySelect(afterAll, afterAll.get(i), afterAllLeftpart, finalpart, startsign));
					selectreg.add(1, myll1.mySelect(afterAll, afterAll.get(j), afterAllLeftpart, finalpart, startsign));
					if (selectreg.get(0).equals(selectreg.get(1))) {
					} else {
						isll1++;
					}

				}
			}
		}
		System.out.println();
		if (isll1 == 0) {
			System.out.println("����LL1�ķ�");
		} else {
			System.out.println("��LL1�ķ�");
		}
		System.out.println();
		Stack<Character> myinput = new Stack<Character>();
		/*myinput.push('$');
		myinput.push(')');
		myinput.push('a');
		myinput.push(',');
		myinput.push('a');
		myinput.push('(');
		*/
		

		myinput.push('$');
		myinput.push('i');
		myinput.push('*');
		myinput.push('i');
		myinput.push('*');
		myinput.push('i');
		myinput.push('*');
		myinput.push(')');
		myinput.push('i');
		myinput.push('(');
		
		
		/*myinput.push('$');
		myinput.push(')');
		myinput.push('i');
		myinput.push('(');
		*/
		System.out.println("Ԥ��������£�");
		myll1.predictionAnalysis(myinput, selectmap, startsign);

	}
}

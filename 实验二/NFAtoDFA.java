package NFAtoDFA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import NFAtoDFA.edge;

public class NFAtoDFA {
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

	public String closure(String ss, List<edge> ee) {
		String l = "";
		l += ss;
		for (int i = 0; i < ss.length(); i++) {
			char s = ss.charAt(i);
			for (edge e : ee) {
				if (e.getSecond().equals("#") && e.getFirst().charAt(0) == s) {
					// ss.add(e.getThird());
					l += e.getThird();
				}
			}
		}
		return removeMethod(l);
	}

	public String move(char c, List<edge> ee, String ss) {
		// List<String> moveBych = new ArrayList<String>();
		String l = "";
		for (int i = 0; i < ss.length(); i++) {
			char s = ss.charAt(i);
			for (edge e : ee) {
				if (e.getFirst().charAt(0) == s && e.getSecond().charAt(0) == c) {
					// moveBych.add(e.getThird());
					l += e.getThird();
				}
			}
		}
		l = removeMethod(l);
		l = closure(closure(l, ee), ee);
		return l;
	}

	public String sort(String list) {
		char[] s1 = list.toCharArray();
		// System.out.println(s1);
		for (int i = 0; i < s1.length; i++) {
			for (int j = 0; j < i; j++) {
				if (s1[i] < s1[j]) {
					char temp = s1[i];
					s1[i] = s1[j];
					s1[j] = temp;
				}
			}
		}
		return String.valueOf(s1);
	}

	public static void main(String[] args) {
		int n = 0;
		NFAtoDFA nfatodaf = new NFAtoDFA();
		String pathname = "E://NFA2.txt";
		File filename = new File(pathname);
		String all = null;// ������
		String allcondition = null;// ת���ַ�
		List<edge> allline = new ArrayList<edge>(); // NFAת����
		InputStreamReader read;
		String myfinal = "";
		try {
			read = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(read);
			// ���������
			try {
				all = br.readLine();
				int all_int = Integer.parseInt(all);
				n = all_int;
				System.out.println(all_int);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ���ת���ַ�
			try {
				allcondition = br.readLine();
				// char a = allcondition.charAt(1);
				System.out.println(allcondition);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ���ת����
			for (int i = 0; i < n; i++) {
				try {
					String oneline = br.readLine();
					char first = oneline.charAt(0);
					char second = oneline.charAt(1);
					char third = oneline.charAt(2);
					String sf = new String();
					sf += first;
					String ss = new String();
					ss += second;
					String st = new String();
					st += third;
					edge oline = new edge();
					oline.setFirst(sf);
					oline.setSecond(ss);
					oline.setThird(st);
					allline.add(oline);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				myfinal = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// ��ӡ��ʼ��ÿ�б任
		for (edge e : allline) {
			System.out.print(e.getFirst());
			System.out.print(e.getSecond());
			System.out.print(e.getThird());
			System.out.println();
		}
		edge firstline = allline.get(0);
		String First = firstline.getFirst();// �õ���ʼ�ڵ�
		// System.out.println(First);

		List<String> Dstates = new ArrayList<String>();
		// ������ʼ�ڵ�Ŀձհ���������Dststes
		Dstates.add(nfatodaf.sort(nfatodaf.closure(nfatodaf.closure(First, allline), allline)));
		// for (String t : T)
		/*
		 * ����sort��move�ɹ� String L = T.get(0);
		 * T.add(nfatodaf.sort(nfatodaf.move(allcondition.charAt(0), allline, L)));
		 * System.out.println(550); for (String t : T) { System.out.println(t); }
		 */

		for (int i = 0; i < 20; i++) {
			String ss = "";
			// myregister.set(i, 1);
			for (int j = 0; j < allcondition.length(); j++) {
				ss = nfatodaf.move(allcondition.charAt(j), allline, Dstates.get(i));
				ss = nfatodaf.closure(nfatodaf.closure(ss, allline), allline);
				ss = nfatodaf.sort(ss);

				/*
				 * for (int k = 0; k < Dstates.size(); k++) { if (Dstates.get(i).equals(ss)) {
				 * nothavenew = false; break; } }
				 */
				// if (ss != "") {
				Dstates.add(ss);
				// myregister.add(0);
				// }
			}
			// System.out.println(Dstates);
			// i++;
		}
		// }
		Dstates = new ArrayList<String>(new HashSet<String>(Dstates));// ȥ��

		// �ѿ���ɾ��
		for (int i = 0; i < Dstates.size(); i++) {
			if (Dstates.get(i).isEmpty()) {
				Dstates.remove(i);
			}
		}

		System.out.println("---------DFAת�Ʊ�------------");

		// ����first���dfa
		List<edge> dfas = new ArrayList<edge>();
		for (int i = 0; i < Dstates.size(); i++) {
			for (int j = 0; j < allcondition.length(); j++) {
				edge dfa = new edge();
				dfa.setFirst(Dstates.get(i));

				String chartoString = "";
				chartoString += allcondition.charAt(j);
				dfa.setSecond(chartoString);

				String lastmen = "";
				lastmen = nfatodaf.move(allcondition.charAt(j), allline, dfa.getFirst());
				lastmen = nfatodaf.closure(nfatodaf.closure(lastmen, allline), allline);
				lastmen = nfatodaf.sort(lastmen);
				dfa.setThird(lastmen);
				dfas.add(dfa);
			}
		}

		for (int i = 0; i < dfas.size(); i++) {
			System.out.print(dfas.get(i).getFirst());
			System.out.print(dfas.get(i).getSecond());
			System.out.print(dfas.get(i).getThird());
			System.out.println();
		}
		List<String> classnotfinal = new ArrayList<String>();
		List<String> classfinal = new ArrayList<String>();

		/*
		 * for (int i = 0; i < dfas.size(); i++) { for(int j = 0 ;
		 * j<dfas.get(i).getFirst().length();j++) { if(dfas.get(i).getFirst().charAt(j)
		 * == myfinal.charAt(0)) { classfinal.add(dfas.get(i).getFirst()); }else {
		 * classnotfinal.add(dfas.get(i).getFirst()); } } }
		 */
		for (int i = 0; i < Dstates.size(); i++) {
			for (int j = 0; j < Dstates.get(i).length(); j++) {
				if (Dstates.get(i).charAt(j) == myfinal.charAt(0)) {
					classfinal.add(Dstates.get(i));
					continue;
				} else {
					classnotfinal.add(Dstates.get(i));
				}
			}
		}
		classnotfinal = new ArrayList<String>(new HashSet<String>(classnotfinal));
		// classfinal = new ArrayList<String>(new HashSet<String>(classfinal));
		for (int i = 0; i < classfinal.size(); i++) {
			for (int j = 0; j < classnotfinal.size(); j++) {
				if (classfinal.get(i).equals(classnotfinal.get(j))) {
					classnotfinal.remove(j);
				}
			}
		}

		System.out.println("���λ���DFA�ս���ż���");
		for (int i = 0; i < classfinal.size(); i++) {
			System.out.print(classfinal.get(i));
			System.out.println();
		}
		System.out.println("���λ���DFA���ս���ż���");
		for (int i = 0; i < classnotfinal.size(); i++) {
			System.out.print(classnotfinal.get(i));
			System.out.println();
		}

		List<String> newcla = new ArrayList<String>();
		List<String> realnewcla = new ArrayList<String>();
		for (int j = 0; j < classfinal.size(); j++) {
			for (int i = 0; i < allcondition.length(); i++) {
				for (int k = 0; k < classfinal.size(); k++) {
					if (nfatodaf.move(allcondition.charAt(i), allline, classfinal.get(j)).equals(classfinal.get(k))) {
						newcla.add(classfinal.get(j));
					}
				}

			}
		}
		for (int i = 0; i < newcla.size(); i++) {
			for (int j = 0; j < classfinal.size(); j++) {
				if (newcla.get(i).equals(classfinal.get(j))) {
					classfinal.remove(j);
				}
			}
		}
		realnewcla = classfinal;
		System.out.println("ppppppppppppppppppppppppppp");
		newcla = new ArrayList<String>(new HashSet<String>(newcla));
		for (int i = 0; i < realnewcla.size(); i++) {
			System.out.print(realnewcla.get(i));
			System.out.println();
		}
		System.out.println("ppppppppppppppppppppppppppp");
		for (int i = 0; i < newcla.size(); i++) {
			System.out.print(newcla.get(i));
			System.out.println();
		}
		
		
		List<String> newnotcla = new ArrayList<String>();
		List<String> realnewnotcla = new ArrayList<String>();
		for (int j = 0; j < classnotfinal.size(); j++) {
			for (int i = 0; i < allcondition.length(); i++) {
				for (int k = 0; k < classnotfinal.size(); k++) {
					if (nfatodaf.move(allcondition.charAt(i), allline, classnotfinal.get(j)).equals(classnotfinal.get(k))) {
						newnotcla.add(classnotfinal.get(j));
					}
				}

			}
		}
		for (int i = 0; i < newnotcla.size(); i++) {
			for (int j = 0; j < classnotfinal.size(); j++) {
				if (newnotcla.get(i).equals(classnotfinal.get(j))) {
					classnotfinal.remove(j);
				}
			}
		}
		realnewnotcla = classnotfinal;
		System.out.println("ssssssssssssssssssss");
		newnotcla = new ArrayList<String>(new HashSet<String>(newnotcla));
		for (int i = 0; i < realnewnotcla.size(); i++) {
			System.out.print(realnewnotcla.get(i));
			System.out.println();
		}
		System.out.println("sssssssssssssssssssssssss");
		for (int i = 0; i < newnotcla.size(); i++) {
			System.out.print(newnotcla.get(i));
			System.out.println();
		}

	}

}

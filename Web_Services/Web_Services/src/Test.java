import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<TestDTO> arr=new ArrayList<>();
		for(int i=1;i<20;i++){
			TestDTO test=new TestDTO();
			test.setArg("test"+i);
			test.setValue(""+i);
			arr.add(test);
		}
		System.out.println("arr:::"+arr.size());
		
		Map<Integer,List<TestDTO>> list=new HashMap<>();
		
		int count=arr.size();
		int k=0;
		List<TestDTO> arr1=new ArrayList<>();
		int j=0;
		int g=0;
		for(TestDTO te:arr){
			
			if(j<4){
			System.out.println("::arr:::"+arr.get(k).getArg()+":::"+arr.get(k).getValue());
			arr1.add(te);
				list.put(g, arr1);
			j++;
			}else{
				j=0;
				arr1=new ArrayList<>();
				arr1.add(te);
				j++;
				g++;
			}
			k++;
		}
		System.out.println("arr1:::"+list.size());
		for (Map.Entry<Integer, List<TestDTO>> entry : list.entrySet()) {
			System.out.println("::entry:::"+entry.getKey());
			for(TestDTO tr:entry.getValue()){
			System.out.println("::entry:::"+tr.getArg()+":::"+tr.getValue());
			}
		}
		
		}
	}



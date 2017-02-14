/**
 * 
 */
package ua.khpi.kolodiazhny_nikolay.art1;


/**
 * @author kolodiazhny nikolay
 *
 */
public class neuralNetwork {
	    private static final int n = 5;    // ����� ��������� �� ������� ������� ��� �����������;
	    private static final int m = 4;    // ������������ ����� ������������ ��������� � Y-���� ��� ������������ ����� �������������� ������� ������� �����������;
	    private static final double VIGILANCE = 0.4;
	    private static final int PATTERNS = 4;
	    private static final int TRAINING_PATTERNS = 5;  // ����� ��������� ������� ��������
	    private static int pattern[][] = null;           // ������� ��� ��������.

	    private static double bw[][] = null;        //���������� ����
	    private static double tw[][] = null;        //���������� ����

	    private static int f1a[] = null;            //������� ����.
	    private static int f1b[] = null;            //������������ ����
	    private static double f2[] = null;
	    
	    public static void initialize()
	    {
	        pattern = new int[][] {{1, 1, 0, 0, 0}, 
	                               {0, 0, 1, 1, 0}, 
	                               {1, 0, 0, 0, 0},  
	                               {0, 0, 0, 1 ,1}};
	                               
	        System.out.println("������������� ���������� ����");
	        System.out.println(" ");
	        System.out.println("������������� ������� ����� �");
	        bw = new double[m][n];
	        for(int i = 0; i < m; i++)
	        {
	            for(int j = 0; j < n; j++)
	            {
	                bw[i][j] = 1.0 / (1.0 + n);
	                System.out.print(bw[i][j] + ", ");
	            } // j
	            System.out.print("\n");
	        } // i
	        
	        System.out.println();
	        
	        System.out.println("������������� ������� ����� �");
	        tw = new double[m][n];
	        for(int i = 0; i < m; i++)
	        {
	            for(int j = 0; j < n; j++)
	            {
	                tw[i][j] = 1.0;
	                System.out.print(tw[i][j] + ", ");
	            } // j
	            System.out.print("\n");
	        } // i
	        System.out.println();
	        
	        f1a = new int[n];
	        f1b = new int[n];
	        f2 = new double[m];

	        return;
	    }
	   
	    private static int vectorSum(int[] nodeArray)
	    {
	        int sum = 0;
	        // ������� ����� ��������� �������� �������
	        for(int i = 0; i < n; i++)
	        {
	            sum += nodeArray[i];
	        }
	        return sum;
	    }
	    
	    private static void updateWeights(int activationSum, int f2Max)
	    {
	        //��������� ����� ������� bw(f2Max)
	        for(int i = 0; i < n; i++)
	        {
	            bw[f2Max][i] = (2.0 * (double)f1b[i]) / (1.0 + (double)activationSum);
	        }
	        for(int i = 0; i < n; i++)
	        {
	            for(int j = 0; j < m; j++)
	            {
	                System.out.print(String.format("%.1f", bw[j][i]) + ", ");
	            } //j
	            System.out.println();
	        } // i
	        System.out.println();
	        
	        //��������� ����� ������� tw(f2Max)
	        for(int i = 0; i < n; i++)
	        {
	            tw[f2Max][i] = f1b[i];
	        }
	        
	        for(int i = 0; i < m; i++)
	        {
	            for(int j = 0; j < n; j++)
	            {
	                System.out.print(String.format("%.1f", tw[i][j]) + ", ");
	            } // j
	            System.out.println();
	        } // i
	        System.out.println();

	        return;
	    }
	    
	    private static boolean testForReset(int activationSum, int inputSum, int f2Max)
	    {
	        if((double)activationSum / (double)inputSum >= VIGILANCE){
	            return false; // �������� ������������ 
	        }else{
	            f2[f2Max] = -1.0; 
	            return true; // �������� ��������
	        }
	    }
	    
	    private static int maximum(double[] nodeArray)
	    {
	        int winner = 0;
	        boolean foundNewWinner = false;
	        boolean done = false;

	        while(!done)
	        {
	            foundNewWinner = false;
	            for(int i = 0; i < m; i++)
	            {
	                if(i != winner){             // ��������� ���������������� ���������
	                    if(nodeArray[i] > nodeArray[winner]){
	                        winner = i;
	                        foundNewWinner = true;
	                    }
	                }
	            }

	            if(foundNewWinner == false){
	                done = true;
	            }
	        }
	        return winner;
	    }
	    
	    public static void printResults()
	    {
	        System.out.println("�������� ��������� ����:");
	        for(int i = 0; i < m; i++)
	        {
	            for(int j = 0; j < n; j++)
	            {
	                System.out.print(String.format("%.1f", bw[i][j]) + ", ");
	            } // j
	            System.out.print("\n");
	        } // i
	        System.out.println();
	        
	        for(int i = 0; i < m; i++)
	        {
	            for(int j = 0; j < n; j++)
	            {
	                System.out.print(String.format("%.1f", tw[i][j]) + ", ");
	            } // j
	            System.out.print("\n");
	        } // i

	        return;
	    }
	   
	    
	    public static void ART1()
	    {
	        int inputSum = 0;
	        int activationSum = 0;
	        int f2Max = 0;
	        boolean reset = true;

	        System.out.println("�������� ���� ART1.\n");
	        System.out.println(" ");
	        System.out.println("������ ����������:\n");
	        for(int vecNum = 0; vecNum < PATTERNS; vecNum++)
	        {
	            System.out.println("������: " + (vecNum+1) + "\n");

	            // ������������� ������� f2 0.0
	            System.out.println("�������� �������� ������� ��������� Y-����:");
	            for(int i = 0; i < m; i++)
	            {
	                f2[i] = 0.0;
	            }

	            // ��� pattern() ��� f1 
	            for(int i = 0; i < n; i++)
	            {
	                f1a[i] = pattern[vecNum][i];
	            }

	            // ����������� ����� ������� �������� �������� �������� ����
	            System.out.println("����������� ����� ������� �������� �������� �������� ����:");
	            inputSum = vectorSum(f1a);
	            System.out.println("����� ������� = " + inputSum + "\n");

	            // ������ ��������� ��� ������� ���� ������� ����
	            //  ����������� ������� ������� � �������� �������� ��������� ������������� ����:
	            System.out.println("����������� ������� ������� � �������� �������� ��������� ������������� ����.");
	            for(int i = 0; i < n; i++)
	            {
	                f1b[i] = f1a[i];
	            }
	            
	            // �����������  �������  �������  ���  ����  ���������  Y-����:
	            System.out.println("�����������  �������  �������  ���  ����  ���������  Y-����:");
	            for(int i = 0; i < m; i++)
	            {
	                for(int j = 0; j < n; j++)
	                {
	                    f2[i] += bw[i][j] * (double)f1a[j];
	                    System.out.print(String.format("%.1f", f2[i]) + ", ");
	                } // j
	                System.out.println();
	            } // i
	            System.out.println();

	            reset = true;
	            while(reset == true)
	            {
	            	System.out.println("�������������� �������� ������� Z-���������:");
	                // �������������� �������� ������� Z-���������
	                f2Max = maximum(f2);

	                
	                for(int i = 0; i < n; i++)
	                {
	                    System.out.println(f1b[i] + " * " + String.format("%.1f", tw[f2Max][i]) + " = " + String.format("%.1f", f1b[i] * tw[f2Max][i]));
	                    f1b[i] = f1a[i] * (int)Math.floor(tw[f2Max][i]);
	                }

	                // ������ ����� ��������� �������� �������
	                System.out.println("����������� ����� �������:");
	                activationSum = vectorSum(f1b);
	                System.out.println("����� �������� (x(i)) = " + activationSum + "\n");

	                reset = testForReset(activationSum, inputSum, f2Max);

	            }

	            // ������������ ������ ���������� TRAINING_PATTERNS ��� ��������, ��������� ��� ������.
	            if(vecNum < TRAINING_PATTERNS){
	                updateWeights(activationSum, f2Max);
	            }

	            System.out.println("������ #" + (vecNum+1) + " ����������� �������� #" + (f2Max+1) + "\n");

	        } // vecNum
	        return;
	    }
	    
}

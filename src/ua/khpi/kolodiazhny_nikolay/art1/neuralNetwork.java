/**
 * 
 */
package ua.khpi.kolodiazhny_nikolay.art1;


/**
 * @author kolodiazhny nikolay
 *
 */
public class neuralNetwork {
	    private static final int n = 5;    // число компонент во входном векторе или изображении;
	    private static final int m = 4;    // максимальное число распознающих элементов в Y-слое или максимальное число распознаваемых классов входных изображений;
	    private static final double VIGILANCE = 0.4;
	    private static final int PATTERNS = 4;
	    private static final int TRAINING_PATTERNS = 5;  // число обучающих входных векторов
	    private static int pattern[][] = null;           // патерны для обучения.

	    private static double bw[][] = null;        //Восходящие веса
	    private static double tw[][] = null;        //Низходящие веса

	    private static int f1a[] = null;            //Входной слой.
	    private static int f1b[] = null;            //Интерфейсный слой
	    private static double f2[] = null;
	    
	    public static void initialize()
	    {
	        pattern = new int[][] {{1, 1, 0, 0, 0}, 
	                               {0, 0, 1, 1, 0}, 
	                               {1, 0, 0, 0, 0},  
	                               {0, 0, 0, 1 ,1}};
	                               
	        System.out.println("Инициализация параметров сети");
	        System.out.println(" ");
	        System.out.println("Инициализация матрицы весов В");
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
	        
	        System.out.println("Инициализация матрицы весов Т");
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
	        // Подсчет суммы елементов входного вектора
	        for(int i = 0; i < n; i++)
	        {
	            sum += nodeArray[i];
	        }
	        return sum;
	    }
	    
	    private static void updateWeights(int activationSum, int f2Max)
	    {
	        //адаптация весов матрицы bw(f2Max)
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
	        
	        //адаптация весов матрицы tw(f2Max)
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
	            return false; // кандидат принимаеться 
	        }else{
	            f2[f2Max] = -1.0; 
	            return true; // кандидат отклонен
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
	                if(i != winner){             // избегайте самостоятельного сравнения
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
	        System.out.println("Значение конечного веса:");
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

	        System.out.println("Обучение сети ART1.\n");
	        System.out.println(" ");
	        System.out.println("Начало вичислений:\n");
	        for(int vecNum = 0; vecNum < PATTERNS; vecNum++)
	        {
	            System.out.println("Вектор: " + (vecNum+1) + "\n");

	            // инициализация матрицы f2 0.0
	            System.out.println("Задаются выходные сигналы элементов Y-слоя:");
	            for(int i = 0; i < m; i++)
	            {
	                f2[i] = 0.0;
	            }

	            // вод pattern() для f1 
	            for(int i = 0; i < n; i++)
	            {
	                f1a[i] = pattern[vecNum][i];
	            }

	            // Вычисляется норма вектора выходных сигналов входного слоя
	            System.out.println("Вычисляется норма вектора выходных сигналов входного слоя:");
	            inputSum = vectorSum(f1a);
	            System.out.println("Норма вектора = " + inputSum + "\n");

	            // расчет активации для каждого узла первого слоя
	            //  Формируются вектора входных и выходных сигналов элементов интерфейсного слоя:
	            System.out.println("Формируются вектора входных и выходных сигналов элементов интерфейсного слоя.");
	            for(int i = 0; i < n; i++)
	            {
	                f1b[i] = f1a[i];
	            }
	            
	            // Вычисляются  входные  сигналы  для  всех  элементов  Y-слоя:
	            System.out.println("Вычисляются  входные  сигналы  для  всех  элементов  Y-слоя:");
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
	            	System.out.println("Рассчитываются выходные сигналы Z-элементов:");
	                // Рассчитываются выходные сигналы Z-элементов
	                f2Max = maximum(f2);

	                
	                for(int i = 0; i < n; i++)
	                {
	                    System.out.println(f1b[i] + " * " + String.format("%.1f", tw[f2Max][i]) + " = " + String.format("%.1f", f1b[i] * tw[f2Max][i]));
	                    f1b[i] = f1a[i] * (int)Math.floor(tw[f2Max][i]);
	                }

	                // Расчет суммы елементов входного вектора
	                System.out.println("Вычисляется норма вектора:");
	                activationSum = vectorSum(f1b);
	                System.out.println("Норма вектораы (x(i)) = " + activationSum + "\n");

	                reset = testForReset(activationSum, inputSum, f2Max);

	            }

	            // Использовать только количество TRAINING_PATTERNS для обучения, остальные для тестов.
	            if(vecNum < TRAINING_PATTERNS){
	                updateWeights(activationSum, f2Max);
	            }

	            System.out.println("Вектор #" + (vecNum+1) + " принадлежит кластеру #" + (f2Max+1) + "\n");

	        } // vecNum
	        return;
	    }
	    
}

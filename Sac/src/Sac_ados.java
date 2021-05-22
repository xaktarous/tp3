import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.concert.*;
import ilog.cplex.*;
import java.util.Scanner;
public class Sac_ados {
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Scanner sc=new Scanner(System.in);
        System.out.print(" Entrer le poid maximum :");
        int n=sc.nextInt();
        System.out.print(" Entrer le nombre d'objets :");
        int m=sc.nextInt();

        int [][]tab=new int[m][3];


        for(int i=0;i<m;i++){

            System.out.println("c " +i );

            tab[i][0]=sc.nextInt();
            System.out.println("p " +i );

            tab[i][1]=sc.nextInt();

            tab[i][2]=tab[i][0]/tab[i][1];


        }



        System.out.println("**** Avant le tri *****");
        displayTab(tab);
        //tri d un tableau avec l algorithme de tri par selection
        tri(tab);

        System.out.println("**** Apres le tri ****");
        displayTab(tab);

        int [][]t=new int[m][2];
        for(int i=0;i<m;i++) {
            t[i][0]=tab[i][0];
            t[i][1]=tab[i][1];
        }

        calcul (n,m,t);

    }
    public static void tri(int[][] tab)
    {
        for (int i = 0; i < tab.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < tab.length; j++)
            {
                if (tab[j][2] >tab[index][2]){
                    index = j;
                }
            }
            for(int j=0;j<3;j++) {
                int min = tab[index][j];
                tab[index][j] = tab[i][j];
                tab[i][j] = min;
            }
        }
    }
    static void displayTab(int[][] tab){
        for(int i=0; i < tab.length; i++)
        {
            System.out.print( "c: "+tab[i][0] );
            System.out.print( " p: "+tab[i][1] );
            System.out.print( " c/p: "+tab[i][2] );

            System.out.print( " | ");
        }
        System.out.println();
    }
    public static void calcul(int n , int m, int pr [][]) {



        try {
            // define new model
            IloCplex opl = new IloCplex();
            IloNumVar[][] x = new IloNumVar[m][1];
            for(int i=0;i<m;i++) {

                x[i][0]= opl.numVar(0,n);

            }
            //max
            IloLinearNumExpr objective = opl.linearNumExpr();
            for (int i=0; i<n; i++) {

                objective.addTerm(pr[i][0],x[i][0]);

            }
            opl.addMaximize(objective);
            // contraintes

            //permutation de matrice x
         /*   IloNumVar[][] tr = new IloNumVar[m][m];

            for(int i=0;i<m;i++) {
                tr[i][0]=x[i][1];
                tr[i][1]=x[i][0];

            }

            for (int i=0; i<m; i++) {
                opl.addLe(opl.sum(tr[i]),n);

            }
*/




            opl.solve();
            System.out.println("Voici la valeur de la fonction objectif : "+opl.getObjValue() +"\n");

            System.out.println("Voici les valeurs des variables de d�cision:  ");
            for (int i=0;i<n;i++) {
                System.out.println("---------------------------------------------- " );
                for (int j=0; j<n; j++) {

                    System.out.println( "X"+i+ ""+j+"= "+ opl.getValue(x[i][j]));}
            }

            opl.end();

        }catch(IloException exc){
            System.out.print("Exception lev�e " + exc);;
        }
    }
}

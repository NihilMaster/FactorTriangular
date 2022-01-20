import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author M
 */

public class Factor extends JFrame{
    
    public Factor() {
        
        ViewMatriz vm = new ViewMatriz();
        Form form = new Form();
        
        Object A[][]=new Object[3][3];
        form.in.addActionListener((ActionEvent e) ->{
            A[0][0]=form.txt1.getText();A[0][1]=form.txt2.getText();A[0][2]=form.txt3.getText(); 
            A[1][0]=form.txt4.getText();A[1][1]=form.txt5.getText();A[1][2]=form.txt6.getText();
            A[2][0]=form.txt7.getText();A[2][1]=form.txt8.getText();A[2][2]=form.txt9.getText();
            int aux=0;
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    try {aux = Integer.parseInt((String)A[i][j]);} catch (NumberFormatException err) {System.out.println("Campo vacío o erróneo, se sustitituyó con 0");}
                    A[i][j] = aux;
                    aux = 0;
                }
            }
            
            ArrayList<Object[][]> _LU = LU(A);
            ArrayList<Object[][]> correcto = correct(_LU.get(1), _LU.get(0));
            Object L[][] = correcto.get(0);
            Object U[][] = correcto.get(1);
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if("java.lang.String".equals(L[i][j].getClass().getName())){        
                        L[i][j] = new Fraccion(Integer.parseInt(((String)L[i][j]).split("/")[0]),Integer.parseInt(((String)L[i][j]).split("/")[1]));
                    }
                    if("java.lang.String".equals(U[i][j].getClass().getName())){        
                        U[i][j] = new Fraccion(Integer.parseInt(((String)U[i][j]).split("/")[0]),Integer.parseInt(((String)U[i][j]).split("/")[1]));
                    }
                    if("Fraccion".equals(L[i][j].getClass().getName())){ 
                        L[i][j]=enter(((Fraccion)L[i][j]).getNumerador(),((Fraccion)L[i][j]).getDenominador());                        
                        if((int)((Fraccion)L[i][j]).getDenominador()<0){
                            ((Fraccion)L[i][j]).setN(-((Fraccion)L[i][j]).getNumerador());
                            ((Fraccion)L[i][j]).setD(-((Fraccion)L[i][j]).getDenominador());
                        }
                    }
                    if("Fraccion".equals(U[i][j].getClass().getName())){ 
                        U[i][j]=enter(((Fraccion)U[i][j]).getNumerador(),((Fraccion)U[i][j]).getDenominador());                        
                        if((int)((Fraccion)U[i][j]).getDenominador()<0){
                            ((Fraccion)U[i][j]).setN(-((Fraccion)U[i][j]).getNumerador());
                            ((Fraccion)U[i][j]).setD(-((Fraccion)U[i][j]).getDenominador());
                        }
                    }
                }
            }
            
            vm.a1.setText(""+A[0][0]);vm.a2.setText(""+A[0][1]);vm.a3.setText(""+A[0][2]);
            vm.a4.setText(""+A[1][0]);vm.a5.setText(""+A[1][1]);vm.a6.setText(""+A[1][2]);
            vm.a7.setText(""+A[2][0]);vm.a8.setText(""+A[2][1]);vm.a9.setText(""+A[2][2]);
            
            vm.l1.setText(""+L[0][0]);vm.l2.setText(""+L[0][1]);vm.l3.setText(""+L[0][2]);
            vm.l4.setText(""+L[1][0]);vm.l5.setText(""+L[1][1]);vm.l6.setText(""+L[1][2]);
            vm.l7.setText(""+L[2][0]);vm.l8.setText(""+L[2][1]);vm.l9.setText(""+L[2][2]);
            
            vm.u1.setText(""+U[0][0]);vm.u2.setText(""+U[0][1]);vm.u3.setText(""+U[0][2]);
            vm.u4.setText(""+U[1][0]);vm.u5.setText(""+U[1][1]);vm.u6.setText(""+U[1][2]);
            vm.u7.setText(""+U[2][0]);vm.u8.setText(""+U[2][1]);vm.u9.setText(""+U[2][2]);
            
            vm.frame.setSize(400, 150);
            
            inker(A, L, U);
            System.out.println("\nValores ingresados");
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    System.out.print("\t"+A[i][j]);
                }
                System.out.println("\n");
            }
        });
    }
    
    private ArrayList<Object[][]> LU (Object A[][]){
        ArrayList<Object[][]> _LU = new ArrayList<>();
        Object L[][] = new Object[3][3];
        Object U[][] = new Object[3][3];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                U[i][j] = A[i][j];
                L[i][j] =(i==j)?1:0;
            }
        }
        
        // F2->F1
        if ((int)U[1][0] != 0 && (int)U[0][0] != 0){
            if ((int)U[1][0]%(int)U[0][0] == 0){
                L[1][0] = (int)U[1][0]/(int)U[0][0];
                for (int i=0; i<3; i++){U[1][i]=-(((int)L[1][0])*(int)U[0][i]-(int)U[1][i]);}
            }else{
                Fraccion f1 = new Fraccion((int)U[1][0], (int)U[0][0]);
                L[1][0] = f1;
                for (int i=0; i<3; i++){U[1][i]=((f1.producto(new Fraccion((int)U[0][i], -1)).suma(new Fraccion((int)U[1][i],1))).toString());}
            }
        }
        
        // F3->F1
        if ((int)U[2][0] != 0 && (int)U[0][0] != 0){
            if ((int)U[2][0]%(int)U[0][0] == 0){
                L[2][0] = (int)U[2][0]/(int)U[0][0];
                for (int i=0; i<3; i++){U[2][i]=-(((int)L[2][0])*(int)U[0][i]-(int)U[2][i]);}
            }else{
                Fraccion f1 = new Fraccion((int)U[2][0], (int)U[0][0]);
                L[2][0] = f1;
                for (int i=0; i<3; i++){U[2][i]=((f1.producto(new Fraccion((int)U[0][i], -1)).suma(new Fraccion((int)U[2][i],1))).toString());}            
            }
        }
        
        // F3->F2
        if ("java.lang.String".equals(U[2][1].getClass().getName()) || "java.lang.String".equals(U[1][1].getClass().getName())){
            Fraccion f1 = new Fraccion();
            if ("java.lang.String".equals(U[2][1].getClass().getName()) && "java.lang.String".equals(U[1][1].getClass().getName())){
                int D1n = (Integer.parseInt(((String)U[2][1]).split("/")[0]));
                int D1d = (Integer.parseInt(((String)U[2][1]).split("/")[1]));
                int D2n = (Integer.parseInt(((String)U[1][1]).split("/")[0]));
                int D2d = (Integer.parseInt(((String)U[1][1]).split("/")[1]));
                f1 = new Fraccion(D1n*D2d, D2n*D1d);
            }else if ("java.lang.String".equals(U[2][1].getClass().getName()) && !"java.lang.String".equals(U[1][1].getClass().getName())){
                int D1n = (Integer.parseInt(((String)U[2][1]).split("/")[0]));
                int D1d = (Integer.parseInt(((String)U[2][1]).split("/")[1]));
                f1 = new Fraccion(D1n, D1d*(int)U[1][1]);
            }else if (!"java.lang.String".equals(U[2][1].getClass().getName()) && "java.lang.String".equals(U[1][1].getClass().getName())){
                int D2n = (Integer.parseInt(((String)U[1][1]).split("/")[0]));
                int D2d = (Integer.parseInt(((String)U[1][1]).split("/")[1]));
                f1 = new Fraccion(D2d*(int)U[2][1], D2n);
            }
            L[2][1] = enter(f1.getNumerador(),f1.getDenominador());
            for (int i=1; i<3; i++){
                Fraccion p = new Fraccion(-Integer.parseInt(U[1][i].toString().split("/")[0]),Integer.parseInt(U[1][i].toString().split("/")[1]));
                Fraccion r = new Fraccion(-Integer.parseInt(U[2][i].toString().split("/")[0]),Integer.parseInt(U[2][i].toString().split("/")[1]));
                p = enter(p.getNumerador(), p.getDenominador());
                r = enter(r.getNumerador(), r.getDenominador());
                Fraccion f = ((Fraccion)L[2][1]).producto(p).resta(r);
                System.out.println("L: "+L[2][1]);
                System.out.println("p: "+p);
                System.out.println("r: "+r);
                System.out.println("f: "+f);
                U[2][i] = enter(f.getNumerador(),f.getDenominador());
            }
        }
        else if ((int)U[2][1] != 0 && (int)U[1][1] != 0){
            if ((int)U[2][1]%(int)U[1][1] == 0){
                L[2][1] = (int)U[2][1]/(int)U[1][1];
                for (int i=0; i<3; i++){U[2][i]=-(((int)L[2][1])*(int)U[1][i]-(int)U[2][i]);}
            }else{
                Fraccion f1 = new Fraccion((int)U[2][1], (int)U[1][1]);
                L[2][1] = f1;
                for (int i=0; i<3; i++){U[2][i]=((f1.producto(new Fraccion((int)U[1][i], -1)).suma(new Fraccion((int)U[2][i],1))).toString());}
            }
        }
        
        _LU.add(U); _LU.add(L);
        return _LU;
    }
    
    private void inker(Object A[][], Object L[][], Object U[][]){
        System.out.println("\nA");
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print("\t"+A[i][j]);
            }
            System.out.println("\n");
        }
        System.out.println("\nL");
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print("\t"+L[i][j]);
            }
            System.out.println("\n");
        }
        System.out.println("\nU");
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print("\t"+U[i][j]);
            }
            System.out.println("\n");
        }
    }
    
    private ArrayList<Object[][]> correct (Object L[][], Object U[][]){
        ArrayList<Object[][]> correcto = new ArrayList<>();
        
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if("java.lang.String".equals(U[i][j].getClass().getName())){ 
                    String[] parts = ((String)U[i][j]).split("/");
                    int numerador = Integer.parseInt(parts[0]), denominador = Integer.parseInt(parts[1]);
                    if(numerador == 0 || denominador == 0){U[i][j] = 0;}
                    else if(Math.max(Math.abs(numerador), Math.abs(denominador))%Math.min(Math.abs(numerador), Math.abs(denominador)) == 0){
                        U[i][j] = entero(numerador, denominador);
                    }else if(numerador<0 && denominador<0){
                        U[i][j] = ""+Math.abs(numerador)+"/"+Math.abs(denominador);
                    }
                }
                if("Fraccion".equals(L[i][j].getClass().getName())){ 
                    String[] parts = ((Fraccion)L[i][j]).toString().split("/");
                    int numerador = Integer.parseInt(parts[0]), denominador = Integer.parseInt(parts[1]);
                    if(numerador == 0 || denominador == 0){L[i][j] = 0;}
                    else if(Math.max(numerador, denominador)%Math.min(numerador, denominador) == 0){
                        L[i][j] = entero(numerador, denominador);
                    } else if(numerador<0 && denominador<0){
                        L[i][j] = ""+Math.abs(numerador)+"/"+Math.abs(denominador);
                    }
                }
            }
        }
        
        
        correcto.add(L); correcto.add(U);
        return correcto;
    }
    
    private String entero(int num, int den){
        int n = num, d = den, temp;        
        while (n != 0) {
            temp = n;
            n = d % n;
            d = temp;
        }
        return (num/d)+"/"+(den/d);
    }
    
    private Fraccion enter(int num, int den){
        int n = num, d = den, temp;                
        if(num==0 || den==0){
            return new Fraccion(0,0);
        }else{
            while (n != 0) {
                temp = n;
                n = d % n;
                d = temp;
            }
        }
        return new Fraccion(num/d,den/d); 
    }
    
    public static void main(String[] args) throws IOException{
        var run = new Factor();
        System.out.println(run);
    }
}

class Form extends JFrame{
    
    JFrame frame;
    JPanel panelIN;
    JButton in;
    JLabel descrip;
    JTextField txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;

    public Form() {
        panelIN = new JPanel ();
        panelIN.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelIN.setLayout(new GridLayout(3,3,8,8));
        descrip = new JLabel("Ingrese la matriz");
        txt1 = new JTextField("",4);txt2 = new JTextField("",4);txt3 = new JTextField("",4); 
        txt4 = new JTextField("",4);txt5 = new JTextField("",4);txt6 = new JTextField("",4);
        txt7 = new JTextField("",4);txt8 = new JTextField("",4);txt9 = new JTextField("",4);
        in = new JButton("Aceptar");
        panelIN.add(txt1); panelIN.add(txt2); panelIN.add(txt3);
        panelIN.add(txt4); panelIN.add(txt5); panelIN.add(txt6);
        panelIN.add(txt7); panelIN.add(txt8); panelIN.add(txt9);
        
        JOptionPane.showMessageDialog(frame,
            "Si un campo ingresado contiene letras"
          + "\neste campo será tomado como 0.\n\n"
          + "No se admiten decimales, se tomarán como\n"
          + "0 si son ingresados.\n\n"
          + "No ingresar valores de 0 en la matriz\n"
          + "triangular superior.",
            "Información.",
            JOptionPane.WARNING_MESSAGE);
        
        frame =new JFrame("Mateo L.");
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(descrip);
        frame.add(panelIN);
        frame.add(in);
        frame.pack();
        frame.setLocationRelativeTo(null);        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

class ViewMatriz extends JFrame{
    
    JFrame frame;
    JPanel matriz, panelA, panelL, panelU, bA, bL, bU;
    JLabel descrip,igual,por;
    JLabel a1,a2,a3,a4,a5,a6,a7,a8,a9;
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
    JLabel u1,u2,u3,u4,u5,u6,u7,u8,u9;

    public ViewMatriz() {
        panelA = new JPanel ();
        panelA.setLayout(new GridLayout(3,3,8,8));
        
        panelL = new JPanel ();
        panelL.setLayout(new GridLayout(3,3,8,8));
        
        panelU = new JPanel ();
        panelU.setLayout(new GridLayout(3,3,8,8));
        
        bA=new JPanel();bL=new JPanel();bU=new JPanel();
        
        descrip = new JLabel(" A = L * U ");
        igual   = new JLabel(" = ");
        por     = new JLabel(" * ");
        // A
        a1=new JLabel();a2=new JLabel();a3=new JLabel();
        a4=new JLabel();a5=new JLabel();a6=new JLabel();
        a7=new JLabel();a8=new JLabel();a9=new JLabel();
        // L
        l1=new JLabel();l2=new JLabel();l3=new JLabel();
        l4=new JLabel();l5=new JLabel();l6=new JLabel();
        l7=new JLabel();l8=new JLabel();l9=new JLabel();
        // U
        u1=new JLabel();u2=new JLabel();u3=new JLabel();
        u4=new JLabel();u5=new JLabel();u6=new JLabel();
        u7=new JLabel();u8=new JLabel();u9=new JLabel();
        
        // Sty
        panelA.setBorder(BorderFactory.	createMatteBorder(0,2,0,2,Color.GRAY));
        a1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        a3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        a9.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        a7.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        panelL.setBorder(BorderFactory.	createMatteBorder(0,2,0,2,Color.GRAY));
        l1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        l3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        l9.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        l7.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        panelU.setBorder(BorderFactory.	createMatteBorder(0,2,0,2,Color.GRAY));
        u1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        u3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        u9.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        u7.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        
a1.setHorizontalAlignment(SwingConstants.CENTER);a2.setHorizontalAlignment(SwingConstants.CENTER);a3.setHorizontalAlignment(SwingConstants.CENTER);
a4.setHorizontalAlignment(SwingConstants.CENTER);a5.setHorizontalAlignment(SwingConstants.CENTER);a6.setHorizontalAlignment(SwingConstants.CENTER);
a7.setHorizontalAlignment(SwingConstants.CENTER);a8.setHorizontalAlignment(SwingConstants.CENTER);a9.setHorizontalAlignment(SwingConstants.CENTER);

l1.setHorizontalAlignment(SwingConstants.CENTER);l2.setHorizontalAlignment(SwingConstants.CENTER);l3.setHorizontalAlignment(SwingConstants.CENTER);
l4.setHorizontalAlignment(SwingConstants.CENTER);l5.setHorizontalAlignment(SwingConstants.CENTER);l6.setHorizontalAlignment(SwingConstants.CENTER);
l7.setHorizontalAlignment(SwingConstants.CENTER);l8.setHorizontalAlignment(SwingConstants.CENTER);l9.setHorizontalAlignment(SwingConstants.CENTER);
        
u1.setHorizontalAlignment(SwingConstants.CENTER);u2.setHorizontalAlignment(SwingConstants.CENTER);u3.setHorizontalAlignment(SwingConstants.CENTER);
u4.setHorizontalAlignment(SwingConstants.CENTER);u5.setHorizontalAlignment(SwingConstants.CENTER);u6.setHorizontalAlignment(SwingConstants.CENTER);
u7.setHorizontalAlignment(SwingConstants.CENTER);u8.setHorizontalAlignment(SwingConstants.CENTER);u9.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelA.add(a1); panelA.add(a2); panelA.add(a3);
        panelA.add(a4); panelA.add(a5); panelA.add(a6);
        panelA.add(a7); panelA.add(a8); panelA.add(a9);
        
        panelL.add(l1); panelL.add(l2); panelL.add(l3);
        panelL.add(l4); panelL.add(l5); panelL.add(l6);
        panelL.add(l7); panelL.add(l8); panelL.add(l9);
        
        panelU.add(u1); panelU.add(u2); panelU.add(u3);
        panelU.add(u4); panelU.add(u5); panelU.add(u6);
        panelU.add(u7); panelU.add(u8); panelU.add(u9);
        
        bA.add(panelA); bL.add(panelL); bU.add(panelU);
        
        matriz=new JPanel();
        matriz.setLayout(new BoxLayout(matriz,BoxLayout.X_AXIS));
        matriz.add(bA);matriz.add(igual);matriz.add(bL);matriz.add(por);matriz.add(bU);
        
        frame =new JFrame("Mateo L.");
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(descrip);
        frame.add(matriz);
        frame.pack();
        frame.setLocationRelativeTo(null);        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}


class Fraccion {
    private int numerador, denominador;

    // Constructores
    public Fraccion() {
        this.numerador = 1;
        this.denominador = 1;
    }

    public Fraccion(int numerador) {
        this.numerador = numerador;
        this.denominador = 1;
        zero();
    }

    public Fraccion(int numerador, int denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
        zero();
    }

    public Fraccion(Fraccion f) {
        this.numerador = f.getNumerador();
        this.denominador = f.getDenominador();
        zero();
    }
    
    // Getters y setters
    public int getNumerador() {
        return this.numerador;
    }

    public int getDenominador() {
        return this.denominador;
    }
    
    public void setN(int n){
        this.numerador = n;
    }
    
    public void setD(int d){
        this.denominador = d;
    }

    // Métodos principales
    public Fraccion suma(Fraccion otra) {
        int a,b,c,d;
        a=this.getNumerador(); b=this.getDenominador();
        c=otra.getNumerador(); d=otra.getDenominador();
        return new Fraccion(((a*d)+(b*c)),(b*d));
    }
    
    public Fraccion resta(Fraccion otra) {
        int a,b,c,d;
        a=this.getNumerador(); b=this.getDenominador();
        c=otra.getNumerador(); d=otra.getDenominador();
        return new Fraccion(((a*d)-(b*c)),(b*d));
    }

    public Fraccion producto(Fraccion otra) {
        return new Fraccion(this.getNumerador() * otra.getNumerador(), this.getDenominador() * otra.getDenominador());
    }
    
    public Fraccion division(Fraccion otra) {
        return new Fraccion(this.getNumerador() * otra.getDenominador(), this.getDenominador() * otra.getNumerador());
    }
    
    private void zero(){
        if(numerador==0){denominador=0;}
        if(denominador==0){numerador=0;}
    }

    // Heredados
    @Override
    public String toString() {
        return String.valueOf(this.getNumerador()).concat("/").concat(String.valueOf(this.getDenominador()));
    }
}




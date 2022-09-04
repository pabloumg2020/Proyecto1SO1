
import static java.lang.Integer.parseInt;
import javax.swing.table.DefaultTableModel;

public class Hilos extends Thread{
    
    int Contador;//cuenta de procesos creados
    int Nproceso;//numero de proceso
    int Tiempop;//tamaño de proceso
    int Ejecucion=0;//porcentaje de ejecucion
    int Quantum1 = RandomNumber.generateRandom();
    int Quantum2 = 0;
    static DefaultTableModel modelo;
    
    public Hilos (){
        
    }
    
    private void Captura(int i){ //Captura los valores de la Tabla de procesos
        Nproceso = (int)GestorProcesos.jTable1.getValueAt(i,0);
        Tiempop = parseInt((String)(GestorProcesos.jTable1.getValueAt(i,3)));
        Quantum2 = Quantum1;
        Ejecucion = parseInt((String)(GestorProcesos.jTable1.getValueAt(i,4)));
    }
    
    private void Dormir(){
        try{
            Thread.sleep(4); //controla la velocidad de ejecucion
        }catch(InterruptedException ex){
            
        }
    }
    
    public void sumarCpu(){
        int cpu = 0, sCpup = 0, cpud = 0, cput = 0;
        for(int i=0; i<GestorProcesos.jTable1.getRowCount(); i++){
            cpu = Integer.parseInt(GestorProcesos.jTable1.getValueAt(i,2).toString());
            sCpup += cpu;
        }
        cput = Integer.parseInt(GestorProcesos.tfCputotal.getText());
        cpud = cput - sCpup;
        GestorProcesos.tfCpuenuso.setText(""+sCpup);
        GestorProcesos.tfCpudisponible.setText(""+cpud);
    }
    
    public void sumarMemoria(){
        int mem = 0, sMemp = 0, memd = 0, memt = 0;
        for(int i=0; i<GestorProcesos.jTable1.getRowCount(); i++){
            mem = Integer.parseInt(GestorProcesos.jTable1.getValueAt(i,3).toString());
            sMemp += mem;
        }
        memt = Integer.parseInt(GestorProcesos.tfMtotal.getText());
        memd = memt - sMemp;
        GestorProcesos.tfMenuso.setText(""+sMemp);
        GestorProcesos.tfMdisponible.setText(""+memd);
    }
    
    public void Borrar(int b){ //Elimina los datos de la tabla de procesos
        GestorProcesos.jTable1.setValueAt("0",b,2);
        GestorProcesos.jTable1.setValueAt("0",b,3);
        GestorProcesos.jTable1.setValueAt("0",b,4);
    }
    
    public void SetVar(int _Count){
        Contador = _Count;
    }
    
    @Override
    public void run(){
        int process = 1; //variable para la condicion del ciclo
        int i = 0; // contador de while
        
        while(process!=0){ // mientras que el valor del proceso sea diferente de 0
            while(i<Contador){ //recorrer las filas mientras que el contador < cantidad de procesos
                Captura(i); //metodo que obtiene los valores de la tabla y los asigna a variables
                if(Ejecucion!=0 && Ejecucion > Quantum2){ //Ejecutando Proceso
                    for(int p=0; p<=Quantum2; p++){
                        GestorProcesos.jTable1.setValueAt("Ejecutando",i,5);
                        Ejecucion--; //disminuye el tiempo de ejecucion hasta llegar a 0 
                        GestorProcesos.jTable1.setValueAt(String.valueOf(Ejecucion),i,4);
                        sumarCpu();
                        sumarMemoria();
                        Dormir();
                    }
                    GestorProcesos.jTable1.setValueAt("Listo",i,5);
                    if(Ejecucion==0){
                        GestorProcesos.jTable1.setValueAt("Terminado",i,5);
                        sumarCpu();
                        sumarMemoria();
                        Borrar(i);
                    }
                }else{
                    if(Ejecucion>0){
                        while(Ejecucion>0){
                            GestorProcesos.jTable1.setValueAt("Ejecutando",i,5);
                            Ejecucion--;
                            GestorProcesos.jTable1.setValueAt(String.valueOf(Ejecucion),i,4);
                            sumarCpu();
                            sumarMemoria();
                            Dormir();
                        }
                        GestorProcesos.jTable1.setValueAt("Listo",i,5);
                        if(Ejecucion==0){
                            GestorProcesos.jTable1.setValueAt("Terminado",i,5);
                            sumarCpu();
                            sumarMemoria();
                            Borrar(i);
                        }
                    }else{
                        if(Ejecucion==0){
                            GestorProcesos.jTable1.setValueAt("Terminado",i,5);
                            sumarCpu();
                            sumarMemoria();
                            Borrar(i);
                        }
                    }
                }
                i++;
            }
            i=0;           
        }           
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import Otros.Limpiar_txt;
import Otros.imgTabla;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Belen
 */
public class Panel3 extends javax.swing.JFrame {

    /**
     * Creates new form Panel3
     */
    Limpiar_txt lt = new Limpiar_txt();
    
    
    //private String ruta_txt = "src\\Visual\\mi.txt"; 
    
    Integrantes p;
    Proceso rp;
    int clic_tabla;
    File fichero = new File("mi.txt");
    
    public Panel3() {
        initComponents();
        setTitle("Administrador");
        rp= new Proceso();
        this.setIconImage(new ImageIcon(getClass().getResource("/Recursos/73537678_2463897243868204_8414604552279425024_n (2).jpg")).getImage());
        System.out.println(fichero.getAbsolutePath());
        try{
            cargar_txt();
            listarRegistro();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
    }
    
     public void cargar_txt(){
        File ruta = new File("mi.txt");
        try{
            
            FileReader fi = new FileReader(fichero);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea,",");
                p = new Integrantes();
                p.setCodigo(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setNick(st.nextToken());
                p.setCodigodeamistad(st.nextToken());
                p.setContacto(Integer.parseInt(st.nextToken()));
                p.setTeam(st.nextToken());
                rp.agregarRegistro(p);
            }
        
            bu.close();
        }
        catch(Exception ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex);
        }
    } 
     
    public void grabar_txt(){
        FileWriter fw;
//        PrintWriter pw;
        BufferedWriter bw;
        try{
            fw = new FileWriter(fichero);
//            pw = new PrintWriter(fw);
            bw = new BufferedWriter(fw);
            
            
            for(int i = 0; i < rp.cantidadRegistro(); i++){
                p = rp.obtenerRegistro(i);
//                pw.println(String.valueOf(p.getCodigo()+", "+p.getNombre()+", "+p.getNick()+", "+p.getCodigodeamistad()+", "+p.getContacto()+", "+p.getTeam()));
                bw.append(String.valueOf(p.getCodigo()+", "+p.getNombre()+","+p.getNick()+","+p.getCodigodeamistad()+","+p.getContacto()+","+p.getTeam()));
                bw.newLine();
                      
            }
//             pw.close();
             bw.close();
            
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
 //   
    
    public void ingresarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerNick() == null)mensaje("Ingresar Nick");
            else if(leerContacto() == -666) mensaje("Ingresar Contacto");
            else if(leerCodigodeamistad() == null)mensaje("Ingresar Codigo de  amistad");
            else if(leerTeam() == null)mensaje("Ingresar Team");
            else{
                p = new Integrantes(leerCodigo(),leerNick(),leerNombre(),leerCodigodeamistad(),leerContacto(),leerTeam());
                if(rp.buscaCodigo(p.getCodigo())!= -1)mensaje("Este codigo ya existe");
                else rp.agregarRegistro(p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(belu); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    //
    
    public void modificarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerNick() == null)mensaje("Ingresar Nick");
            else if(leerContacto() == -666) mensaje("Ingresar Contacto");
            else if(leerCodigodeamistad() == null) mensaje("Ingresar Codigodeamistad");
            else if(leerTeam() == null)mensaje("Ingresar Team");
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                p = new Integrantes(leerCodigo(), leerNick(), leerNombre(), leerCodigodeamistad(),leerContacto(),leerTeam());
               
                if(codigo == -1)rp.agregarRegistro(p);
                else rp.modificarRegistro(codigo, p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(belu);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    } 
       // 
    public void eliminarRegistro(){
        try{
            if(leerCodigo() == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este integrante","Si/No",0);
                    if(s == 0){
                        rp.eliminarRegistro(codigo);
                        
                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(belu);
                    }
                }
                
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Nick");
        dt.addColumn("Codigo de amistad");
        dt.addColumn("Contacto");
        dt.addColumn("Team");
        
        tabla4.setDefaultRenderer(Object.class, new imgTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < rp.cantidadRegistro(); i++){
            p= rp.obtenerRegistro(i);
            fila[0] = p.getCodigo();
            fila[1] = p.getNick();
            fila[2] = p.getNombre();
            fila[3] = p.getCodigodeamistad();
            fila[4] = p.getContacto();
            fila[5] = p.getTeam();
            dt.addRow(fila);
        }
        
        tabla4.setModel(dt);
        tabla4.setRowHeight(60);
    }
    
    //
    public int leerCodigo(){
        try{
            int codigo = Integer.parseInt(txtcodigo.getText().trim());
            return codigo;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leerNombre(){
        try{
            String nombre = txtnombre.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerNick(){
        try{
            String nick = txtnick.getText().trim().replace(" ", "_");
            return nick;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerCodigodeamistad(){
        try{
            String codigodeamistad = txtcodigodeamistad.getText().trim().replace(" ", "_");
            return codigodeamistad;
        }catch(Exception ex){
            return null;
        }
    }
    public int leerContacto(){
        try{
            int contacto = Integer.parseInt(txtcontacto.getText().trim());
            return contacto;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public Object leerTeam(){
        try{                
            Object team = txtteam.getText().trim();
            return team;
        }catch(Exception ex){
            return null;
        }
    }
    
    public byte[] leerFoto(File ruta){
        try{
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            return icono;
        }catch(Exception ex){
            return null;
        }
        
    }
   
     /*public byte[] leerFoto2(int codigo){
            p = rp.obtenerRegistro(codigo);
            try{
               return p.getFoto();
            }catch(Exception ex){
               return null;
           }*/
    
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
        
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        belu = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        guardar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla4 = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtnick = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtcodigodeamistad = new javax.swing.JTextField();
        txtcontacto = new javax.swing.JTextField();
        txtteam = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        txtRuta = new javax.swing.JTextField();
        buscarFoto = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        Escoba = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        belu.setBackground(new java.awt.Color(0, 0, 0));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("integrantes");

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        tabla4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nick", "Nombre", "Codigo de amistad", "Contacto", "Team"
            }
        ));
        tabla4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla4MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabla4);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Contacto");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Codigo");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Nick");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Nombre");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Codigo de amistad");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Team");

        txtnick.setToolTipText("");

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/30%.gif"))); // NOI18N
        lblFoto.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtRuta.setEditable(false);

        buscarFoto.setText("Buscar foto...");
        buscarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarFotoActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Foto");

        Escoba.setText("limpiar");
        Escoba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EscobaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout beluLayout = new javax.swing.GroupLayout(belu);
        belu.setLayout(beluLayout);
        beluLayout.setHorizontalGroup(
            beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beluLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(beluLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(beluLayout.createSequentialGroup()
                        .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, beluLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel21))
                                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(beluLayout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtnick, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                            .addComponent(txtcodigo)))
                                    .addGroup(beluLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtteam, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                            .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                            .addComponent(txtcodigodeamistad)
                                            .addComponent(txtcontacto))
                                        .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(beluLayout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(jLabel27)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(beluLayout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(lblFoto)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buscarFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Escoba, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        beluLayout.setVerticalGroup(
            beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beluLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel25)
                        .addGroup(beluLayout.createSequentialGroup()
                            .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(beluLayout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel21)
                                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel22)
                                        .addComponent(txtnick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(9, 9, 9)
                                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24)
                                        .addComponent(txtcodigodeamistad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(txtcontacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(lblFoto))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(beluLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addComponent(txtteam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(beluLayout.createSequentialGroup()
                        .addComponent(guardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Escoba, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salir)))
                .addGap(12, 12, 12))
        );

        jMenu3.setText("Ayuda");

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/belu-octavo.gif"))); // NOI18N
        jMenu4.setText("ayuda Rotom!!!");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenu4);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(belu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(belu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarFotoActionPerformed
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        jf.setFileFilter(fil);
        jf.setCurrentDirectory(new File("Fotos"));
        int el = jf.showOpenDialog(this);
        if(el == JFileChooser.APPROVE_OPTION){
            txtRuta.setText(jf.getSelectedFile().getAbsolutePath());
            lblFoto.setIcon(new ImageIcon(txtRuta.getText()));
        }
    }//GEN-LAST:event_buscarFotoActionPerformed

    private void EscobaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EscobaActionPerformed
        Limpiar_txt lp = new Limpiar_txt();
        lp.limpiar_texto(belu);
    }//GEN-LAST:event_EscobaActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);

    }//GEN-LAST:event_guardarActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.modificarRegistro(ruta);

    }//GEN-LAST:event_ModificarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
     this.eliminarRegistro();   
    }//GEN-LAST:event_EliminarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
         this.dispose();
    }//GEN-LAST:event_salirActionPerformed

    private void tabla4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla4MouseClicked
        int seleccion=tabla4.rowAtPoint(evt.getPoint());
        txtcodigo.setText(String.valueOf(tabla4.getValueAt(seleccion,0)));
        txtnick.setText(String.valueOf(tabla4.getValueAt(seleccion,1)));
        txtnombre.setText(String.valueOf(tabla4.getValueAt(seleccion,2)));
        txtcodigodeamistad.setText(String.valueOf(tabla4.getValueAt(seleccion,3)));
        txtcontacto.setText(String.valueOf(tabla4.getValueAt(seleccion,4)));
        txtteam.setText(String.valueOf(tabla4.getValueAt(seleccion,5)));
    }//GEN-LAST:event_tabla4MouseClicked

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        Panel9 p9= new Panel9();
        p9.setVisible(true);
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
      Panel9 p9=new Panel9();
      p9.setVisible(true);
    }//GEN-LAST:event_jMenu4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Panel3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Panel3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Panel3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Panel3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Panel3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Escoba;
    private javax.swing.JButton Modificar;
    private javax.swing.JPanel belu;
    private javax.swing.JButton buscarFoto;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JButton salir;
    private javax.swing.JTable tabla4;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtcodigodeamistad;
    private javax.swing.JTextField txtcontacto;
    private javax.swing.JTextField txtnick;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtteam;
    // End of variables declaration//GEN-END:variables
}

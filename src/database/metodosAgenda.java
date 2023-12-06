package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import database.*;
        
public class metodosAgenda {
    
    private Connection con;
    int id=0;
    String nombre,apellido,numero_telefono,correo_electronico,direccion;
    
    conexion conex = new conexion();
    PreparedStatement stmt;
    ResultSet rs;
        
    public void insertar(String nombre,String apellido,String numero_telefono, String correo_electronico,String direccion){
        
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo_electronico=correo_electronico;
        this.numero_telefono=numero_telefono;
        this.direccion=direccion;
        
        try{
            con=conex.getConnection();
            stmt= con.prepareStatement("insert into contacto(nombre, apellido, numero_telefono, correo_electronico, direccion) values(?,?,?,?,?)");
            stmt.setString(1,nombre);
            stmt.setString(2,apellido);
            stmt.setString(3,numero_telefono);
            stmt.setString(4,correo_electronico);
            stmt.setString(5,direccion);
            stmt.executeUpdate();
            
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }
    }
    
    public int Consultar(String telefono){
        
        this.correo_electronico=telefono;
        String clave="" ;
        
        try{
            con=conex.getConnection();
            stmt= con.prepareStatement("select id from usuarios where telefono=?");
            stmt.setString(1,telefono);
            rs=stmt.executeQuery();
            
            if (rs.next()) {
            clave=rs.getString("id");
            JOptionPane.showMessageDialog(null, "El id es :" +clave);
            id=Integer.parseInt(clave);//valor para return
            
            } else {
            JOptionPane.showMessageDialog(null, "No existe una persona con ese correo");
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }       
        return id;
    }
    
    public void eliminar(String telefono){
        
        this.numero_telefono = telefono;
        
        try{
            con=conex.getConnection();
            stmt= con.prepareStatement("Delete from contacto where numero_telefono = ?");
            stmt.setString(1,numero_telefono);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }
    }
    
    public void modificar(String nombre, String apellido, String numero_telefono, String correo_electronico, String direccion, String numeroTelefono){
        
        try{
            /*
            stmt= con.prepareStatement("update contacto set nombre = ?, apellido = ?, numero_telefono = ?, correo_electronico = ?, direccion = ? where correo_electronico = ?");
            stmt.setString(1,nombre);
            stmt.setString(2,apellido);
            stmt.setString(3,numero_telefono);
            stmt.setString(4,correo_electronico);
            stmt.setString(5,direccion);
            stmt.setString(6,numeroTelefono);
            stmt.executeUpdate();
            */
            con=conex.getConnection();
            String query = "UPDATE `mydb`.`contacto` SET `nombre` = '" + nombre + "', `apellido` = '" + apellido + "', `numero_telefono` = '" + numero_telefono + "', `correo_electronico` = '" + correo_electronico + "', `direccion` = '" + direccion + "' WHERE (`numero_telefono` = '" + numeroTelefono + "');";
            stmt= con.prepareStatement(query);
            stmt.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }
    }
    /*
    public void ConsultarUsuario(String usuario,String contrasenia){
        
        try{
            con=conex.getConexion();
            stmt= con.prepareStatement("select usuario,contrasenia from usuarios where usuario=? and contrasenia=?");
            stmt.setString(1,usuario);
            stmt.setString(2,contrasenia);
            rs=stmt.executeQuery();
            
            if (rs.next()) {
            this.usuario=rs.getString("usuario");
            this.password=rs.getString("contrasenia");
            JOptionPane.showMessageDialog(null,"BIENVENIDO AL SISTEMA");  
            
            } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }       
    }
    */
    public DefaultTableModel ConsultaGeneral(){
        DefaultTableModel modelBD=new DefaultTableModel();
        modelBD.addColumn("Clave");
        modelBD.addColumn("Nombre");
        modelBD.addColumn("Correo");
        modelBD.addColumn("Usuario");
        modelBD.addColumn("Contraseña");
        
        String[] datos=new String[5];
        try{
            con=conex.getConnection();
            stmt= con.prepareStatement("select * from usuarios");
            ResultSet rs1=stmt.executeQuery();
            
            while(rs1.next()) {
               datos[0]=rs1.getString(1);
               datos[1]=rs1.getString(2);
               datos[2]=rs1.getString(3);
               datos[3]=rs1.getString(4);
               datos[4]=rs1.getString(5);              
               modelBD.addRow(datos);
            } 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de consulta:" + e.getMessage());
        }
        return modelBD;
    }
}

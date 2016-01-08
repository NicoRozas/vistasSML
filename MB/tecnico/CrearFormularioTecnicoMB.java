/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.tecnico;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aracelly
 */
@Named(value = "crearFormularioTecnicoMB")
@RequestScoped
@ManagedBean
public class CrearFormularioTecnicoMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioTecnicoMB.class.getName());

    //Guardamos el usuario que inicia sesion
    private Usuario uSesion;

    //Atributos del formulario
    private String ruc;
    private String rit;
    private int nue;
    private String cargo;
    private String delito;
    private String direccionSS;
    private String lugar;
    private String unidad;
    private String levantadaPor;
    private String rut;
    private Date fecha;
    private String observacion;
    private String descripcion;
    private int parte;

    //Guardamos la cuenta del usuario que entrego la vista del login
    private String usuarioSis;

    //Envio del nue
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    //Captura al usuario proveniente del inicio de sesión
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String evidencias;
    private String codTipoEvidencia;
    private String depa;

    public String getDepa() {
        return depa;
    }

    public void setDepa(String depa) {
        this.depa = depa;
    }

    private List<String> listEvidencias = new ArrayList<>();
//    private List<String> listTipo = new ArrayList<>();

    public CrearFormularioTecnicoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioTecnicoMB");
        this.uSesion = new Usuario();
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
        logger.exiting(this.getClass().getName(), "CrearFormularioTecnicoMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosTecnico");
        this.uSesion = (Usuario) usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);

        this.cargo = this.uSesion.getCargoidCargo().getNombreCargo();
        this.levantadaPor = this.uSesion.getNombreUsuario();
        this.unidad = this.uSesion.getUnidad();
        this.rut = this.uSesion.getRutUsuario();

        GregorianCalendar c = new GregorianCalendar();
        this.fecha = c.getTime();

        logger.exiting(this.getClass().getName(), "cargarDatosTecnico");
    }

    public String iniciarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormularioTecnico");
        logger.log(Level.FINEST, "formulario nue {0}", this.nue);
        logger.log(Level.FINEST, "usuario inicia rut {0}", this.rut);
        logger.log(Level.FINEST, "formulario fecha {0}", this.fecha);
        logger.log(Level.FINEST, "usuario inicia cargo {0}", this.cargo);
        String resultado = formularioEJB.crearFormulario(ruc, rit, nue, parte, cargo, delito, direccionSS, lugar, unidad, levantadaPor, rut, fecha, observacion, descripcion, uSesion);

        //Enviando nue
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        //Enviando usuario
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);

        if (resultado.equals("Exito")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resultado, "Datos exitosos"));
            logger.exiting(this.getClass().getName(), "iniciarFormularioTecnico", "formularioCreadoTecnico");
            return "formularioCreadoTecnico?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarFormularioTecnico", "");
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirTecnico");
        logger.log(Level.FINEST, "usuario saliente {0}", this.uSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirTecnico", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    public Usuario getuSesion() {
        return uSesion;
    }

    public void setuSesion(Usuario uSesion) {
        this.uSesion = uSesion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public String getDireccionSS() {
        return direccionSS;
    }

    public void setDireccionSS(String direccionSS) {
        this.direccionSS = direccionSS;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getLevantadaPor() {
        return levantadaPor;
    }

    public void setLevantadaPor(String levantadaPor) {
        this.levantadaPor = levantadaPor;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioSis() {
        return usuarioSis;
    }

    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public int getParte() {
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }

    public String getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }

    public String getCodTipoEvidencia() {
        return codTipoEvidencia;
    }

    public void setCodTipoEvidencia(String codTipoEvidencia) {
        this.codTipoEvidencia = codTipoEvidencia;
    }

    public List<String> getListEvidencias() {
        return listEvidencias;
    }

    public void setListEvidencias(List<String> listEvidencias) {
        this.listEvidencias = listEvidencias;
    }

//    public List<String> getListTipo() {
//        return listTipo;
//    }
//
//    public void setListTipo(List<String> listTipo) {
//        this.listTipo = listTipo;
//    }

    //este funciona
//    public void cargarTipo(AjaxBehaviorEvent event) {
//        logger.info("selecciono: " + depa);
//        System.out.println("seleccioono " + depa);
//
//        switch (depa) {
//            case "1":
//                //Clínica
//                listTipo.add("Biológica");
//                listTipo.add("Artefacto");
//                listTipo.add("Vestuario");
//                listTipo.add("otro");
//                break;
//            case "2":
//                //Tanatología
//                listTipo.add("Biológica");
//                listTipo.add("Artefacto");
//                listTipo.add("Vestuario");
//                listTipo.add("Balística");
//                listTipo.add("otro");
//
//        }
//        
//    }

    public void cargarEvidencias(final AjaxBehaviorEvent event) {
        logger.info("selecciono: " + codTipoEvidencia);
        System.out.println("seleccion: " + codTipoEvidencia);
        System.out.println("entrooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        switch (codTipoEvidencia) {
            case "1":
                //biologica
                listEvidencias.add("contenido bucal");
                listEvidencias.add("contenido vaginal");
                listEvidencias.add("contenido rectal");
                listEvidencias.add("lecho ungeal");
                listEvidencias.add("secreciones");
                listEvidencias.add("sangre");
                listEvidencias.add("orina");
                listEvidencias.add("tejido cerebral");
                listEvidencias.add("tejido de corazón");
                listEvidencias.add("tejido de pulmón");
                listEvidencias.add("tejido de higado");
                listEvidencias.add("tejido de baso");
                listEvidencias.add("tejido de diafragma");
                listEvidencias.add("tejido intestinal");
                listEvidencias.add("tejido de piel");
                listEvidencias.add("otro tipo de tejido");
                listEvidencias.add("otros");
                break;
            case "6":
                //biologica
                listEvidencias.add("contenido bucal");
                listEvidencias.add("contenido vaginal");
                listEvidencias.add("contenido rectal");
                listEvidencias.add("lecho ungeal");
                listEvidencias.add("secreciones");
                listEvidencias.add("sangre");
                listEvidencias.add("orina");
                listEvidencias.add("tejido cerebral");
                listEvidencias.add("tejido de corazón");
                listEvidencias.add("tejido de pulmón");
                listEvidencias.add("tejido de higado");
                listEvidencias.add("tejido de baso");
                listEvidencias.add("tejido de diafragma");
                listEvidencias.add("tejido intestinal");
                listEvidencias.add("tejido de piel");
                listEvidencias.add("otro tipo de tejido");
                listEvidencias.add("otros");
                break;    
            case "2":
                listEvidencias.add("vestido");
                listEvidencias.add("blusa");
                listEvidencias.add("camisa");
                listEvidencias.add("pantalón");
                listEvidencias.add("polera");
                listEvidencias.add("chaqueta");
                listEvidencias.add("chaleco");
                listEvidencias.add("calzado");
                listEvidencias.add("otros");
                //vestuario
                break;
            case "7":
                listEvidencias.add("vestido");
                listEvidencias.add("blusa");
                listEvidencias.add("camisa");
                listEvidencias.add("pantalón");
                listEvidencias.add("polera");
                listEvidencias.add("chaqueta");
                listEvidencias.add("chaleco");
                listEvidencias.add("calzado");
                listEvidencias.add("otros");
                //vestuario
                break;    
            case "3":
                //artefactos
                listEvidencias.add("protector");
                listEvidencias.add("toalla higienica");
                listEvidencias.add("otros");
                break;
            case "8":
                //artefactos
                listEvidencias.add("protector");
                listEvidencias.add("toalla higienica");
                listEvidencias.add("otros");
                break;    
            case "5":
                listEvidencias.add("armas blancas");
                listEvidencias.add("cuchillo");
                listEvidencias.add("sable");
                listEvidencias.add("bala");
                listEvidencias.add("otros");
                break;
            case "4":
                //otros
                listEvidencias.add("otros");
                break;
            case "9":
                //otros
                listEvidencias.add("otros");
                break;

        }
    }

}

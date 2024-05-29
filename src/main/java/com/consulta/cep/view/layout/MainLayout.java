package com.consulta.cep.view.layout;

import java.io.Serial;

import com.consulta.cep.view.cadastro.CadastroForm;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

	@Serial
    private static final long serialVersionUID = 1L;
	
	boolean isAdmin;
	
	String user;
	
    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();
       
        H1 title = new H1("CEP");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs(isAdmin);
       
        
        HorizontalLayout layout = new HorizontalLayout(toggle, title);
        layout.setAlignItems(Alignment.CENTER);
             
        HorizontalLayout horizontalLayout = new HorizontalLayout(layout);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
               
        
        addToDrawer(tabs);
        addToNavbar(horizontalLayout);
    }
    
	private Tabs getTabs(boolean isAdmin) {
        Tabs tabs = new Tabs();
        
        	tabs.add(createTab(VaadinIcon.USER, "Cadastro", CadastroForm.class));

                
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }
    
    private Tab createTab(VaadinIcon viewIcon, String viewName, Class viewClass) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        
        link.setRoute(viewClass);
        link.setTabIndex(-1);

        return new Tab(link);
    }

}

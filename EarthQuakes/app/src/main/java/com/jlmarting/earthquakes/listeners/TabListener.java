package com.jlmarting.earthquakes.listeners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cursomovil on 10/04/15.
 * Esta es una clase que gestiona de forma genérica los fragmentos que
 * se muestran en función del tab seleccionado en la actividad principal
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment fragment;
    private Class<T> fragmentClass;
    private Activity activity;
    private int fragmentContainer;

    public TabListener(int fragmentContainer, Class fragmentClass, Activity activity) {
        this.fragmentContainer = fragmentContainer;
        this.fragmentClass = fragmentClass;
        this.activity = activity;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (this.fragment==null) {
            // Si no lo está ya, instanciar el fragmento y añadirlo al layout
            this.fragment=Fragment.instantiate(this.activity,this.fragmentClass.getName());
            ft.add(android.R.id.content,this.fragment);
        }
        else {
            // Mostrar el fragmento
            ft.attach(this.fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Quitar del layout el fragmento dado que otro será añadido
        if (this.fragment!=null){
            ft.detach(this.fragment);
        }

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Por lo general no se hace nada

    }
}

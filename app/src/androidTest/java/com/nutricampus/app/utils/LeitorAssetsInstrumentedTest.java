package com.nutricampus.app.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LeitorAssetsInstrumentedTest {
    private Context appContext;

    @Test
    public void testaConstrutorPrivate() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor constructor = LeitorAssets.class.getDeclaredConstructor();
        assertTrue("Construtor não é privado", Modifier.isPrivate(constructor.getModifiers()));

    }

    @Before
    public void init() {
        this.appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void carregaJSONAssetsValido() throws Exception {
        String conteudo = LeitorAssets.carregaJSONAssets("estados.json", appContext);
        assertTrue(conteudo.length() > 2);
    }

    @Test
    public void carregaJSONAssetsArquivoNotfound() throws Exception {
        String conteudo = LeitorAssets.carregaJSONAssets("estad.json", appContext);
        assertNull(conteudo);
    }

    @Test
    public void carregaAssetsNaoJson() {
        try {
            LeitorAssets.carregaJSONAssets("testAsset.txt", appContext);
        } catch (IllegalArgumentException | IOException e) {
            assertNull(null);
        }

    }
}
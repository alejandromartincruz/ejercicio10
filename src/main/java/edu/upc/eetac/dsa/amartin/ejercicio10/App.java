/*
 * Copyright 2014 Alejandro Martin Cruz
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.upc.eetac.dsa.amartin.ejercicio10;

/**
 * Hello world!
 *
 */
public class App implements Runnable
{
	String id;
	int cuenta;

	static volatile String UltimaEscritura = "Primero";

	public App (String id, int cuenta) {
		this.id = id;
		this.cuenta = cuenta;
	}
	
	public static synchronized void cambiarEscritura (App proceso) {
		System.out.println (proceso.id + " - " + proceso.cuenta + " Ultimo identificador = " + UltimaEscritura);
		UltimaEscritura = proceso.id;		
	}

	public void run() {
		while (cuenta > 0) {
			try {
			    Thread.sleep(1000);
			}
			catch (Exception ex) {
				System.out.println ("Error");
			}
			cuenta--;
			cambiarEscritura (this);
		}
	}
	
    public static void main( String[] args )
    {
    	App[] contadores = new App[3];
    	contadores[0] = new App("ID1",5);
    	contadores[1] = new App("ID2",7);
    	contadores[2] = new App("ID3",4);

    	Thread[] procesos = new Thread[3];
    	for (int i=0 ; i<contadores.length ; i++) {
    		procesos[i] = new Thread(contadores[i]);
    		procesos[i].start();
    	}
    	
    	int nThreads = 3;
    	while (nThreads > 0) {
    		int nNuevos = 0;
    		for (Thread t: procesos) if (t.isAlive()) nNuevos++;
    		if (nNuevos != nThreads) {
    			System.out.println ("Ultima escritura = " + UltimaEscritura + " " + nNuevos + " threads activos");
    			nThreads = nNuevos;
    		}
    	}
    	
        System.out.println( "Fin del programa" );
    }

}

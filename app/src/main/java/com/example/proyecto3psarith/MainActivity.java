package com.example.proyecto3psarith;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    private TextView menuTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuTextView = findViewById(R.id.menu_text_view);

        // Obtener la hora actual
        LocalTime currentTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentTime = LocalTime.now();
        }

        // Cambiar el menú según la hora actual
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (currentTime.isAfter(LocalTime.parse("08:00")) && currentTime.isBefore(LocalTime.parse("12:00"))) {
                menuTextView.setText("Menú de desayunos");
            } else if (currentTime.isAfter(LocalTime.parse("12:00")) && currentTime.isBefore(LocalTime.parse("18:00"))) {
                menuTextView.setText("Menú de comidas");
            } else if (currentTime.isAfter(LocalTime.parse("18:00")) && currentTime.isBefore(LocalTime.parse("23:00"))) {
                menuTextView.setText("Menú de cenas");
            } else {
                menuTextView.setText("Menú no disponible en este momento");
            }
        }
    }

    public void calcularTotal(View view) {
        // Obtener referencias a los elementos de la interfaz de usuario
        EditText mesaEditText = findViewById(R.id.mesa_edit_text);
        TextView totalTextView = findViewById(R.id.total_text_view);

        // Obtener selecciones de comida y bebidas del cliente y realizar cálculos
        // Supongamos que tienes variables como 'comidaSeleccionada', 'bebidaSeleccionada', etc.
        double subtotal = calcularSubtotal(comidaSeleccionada(), bebidaSeleccionada());
        double impuestos = calcularImpuestos(subtotal);
        double total = subtotal + impuestos;

        // Mostrar el total en el TextView
        totalTextView.setText("Total: $" + total);

        // Guardar la información de la orden en la base de datos o en una lista de pedidos pendientes
        int numeroMesa = Integer.parseInt(mesaEditText.getText().toString());
        guardarOrden(numeroMesa, total);
    }

    private double calcularImpuestos(double subtotal) {
        return 0;
    }

    private void guardarOrden(int numeroMesa, double total) {

    }
    private double calcularSubtotal(String comidaSeleccionada, String bebidaSeleccionada) {
        double precioComida = obtenerPrecioComida(comidaSeleccionada);
        double precioBebida = obtenerPrecioBebida(bebidaSeleccionada);

        return precioComida + precioBebida;
    }

    private double obtenerPrecioComida(String comidaSeleccionada) {
        // Aquí puedes implementar la lógica para obtener el precio de la comida seleccionada
        // Puedes utilizar una base de datos, una lista de precios o cualquier otra fuente de datos
        // En este ejemplo, simplemente se utilizará un conjunto de valores predefinidos

        switch (comidaSeleccionada) {
            case "Omelette":
                return 10.99;
            case "Panqueques":
                return 7.99;
            case "Tostadas":
                return 6.99;
            // Agrega más casos según tus opciones de comida
            default:
                return 0.0; // Precio por defecto si no se encuentra la comida seleccionada
        }
    }

    private double obtenerPrecioBebida(String bebidaSeleccionada) {
        // Aquí puedes implementar la lógica para obtener el precio de la bebida seleccionada
        // De manera similar a obtenerPrecioComida(), puedes utilizar una base de datos o lista de precios

        switch (bebidaSeleccionada) {
            case "Café":
                return 2.99;
            case "Jugo de naranja":
                return 3.49;
            case "Refresco":
                return 1.99;
            // Agrega más casos según tus opciones de bebida
            default:
                return 0.0; // Precio por defecto si no se encuentra la bebida seleccionada
        }
    }

    // Método para obtener la comida seleccionada
    private String comidaSeleccionada() {
        Spinner comidaSpinner = findViewById(R.id.comida_spinner);
        return comidaSpinner.getSelectedItem().toString();
    }

    // Método para obtener la bebida seleccionada
    private String bebidaSeleccionada() {
        ListView bebidaListView = findViewById(R.id.bebida_listview);
        String bebidaSeleccionada = "";

        SparseBooleanArray checkedItems = bebidaListView.getCheckedItemPositions();
        for (int i = 0; i < bebidaListView.getAdapter().getCount(); i++) {
            if (checkedItems.get(i)) {
                bebidaSeleccionada = bebidaListView.getItemAtPosition(i).toString();
                break; // Seleccionamos solo la primera bebida marcada
            }
        }

        return bebidaSeleccionada;
    }

}

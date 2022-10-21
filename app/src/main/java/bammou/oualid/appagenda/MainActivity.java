package bammou.oualid.appagenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // A chque fois qu'on selectionne une date dans le calendrier on va changer cette valeur :
    public static String dateSelectionee = "";
    // Pour stocker les dates comme clés et les évenements comme valeur :
    public static Map<String, String> evenementsPourDate = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CalendarView agenda = (CalendarView) findViewById(R.id.calendarViewAgenda);
        TextView contenuEvenements = (TextView) findViewById(R.id.textViewContenuEven);
        EditText evenement_champ = (EditText) findViewById(R.id.editTextEvenement);
        Button btn_ajouter = (Button) findViewById(R.id.buttonAjouter);

        // aprés le changement de date :
        agenda.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                        @Override

                        // Aprés qu'on selectionne une date :
                        public void onSelectedDayChange(
                                @NonNull CalendarView view,
                                int year,
                                int month,
                                int dayOfMonth) {
                                    // Enregistrer la valeur de la date dans la variable "dateSelectionee"
                                    // On va s'en servir dans OnClick du button "ajouter" (ci-dessous) !
                                    // month + 1 => car l'index commence par 0 !
                                    dateSelectionee = dayOfMonth + "-" + (month + 1) + "-" + year;
                                    // mettre les évènements associés à la date selectionnée dans le TextView :
                                    if (evenementsPourDate.get(dateSelectionee) != null) { // on vérifie déja s'il y a des evens pour cette date!
                                        // sinon on laisse la valeur initiale qui est "pas d'évènement pour cette date!"
                                        contenuEvenements.setText(evenementsPourDate.get(dateSelectionee));
                                    }
                                    else {
                                        contenuEvenements.setText("=> Pas d'évènement pour cette date !");
                                    }
                                }
                        }
         );

        // Evenement onClick pour "Ajouter evenement" :
        btn_ajouter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (evenement_champ.getText() != null) { // si le champ n'est pas vide :
                    String anciensEvenementsPourCetteDate = ""; // Pour éviter l'affichage de "null" dans le cas ou y a pas encore d'even
                    if (evenementsPourDate.get(dateSelectionee) != null) {
                        anciensEvenementsPourCetteDate = evenementsPourDate.get(dateSelectionee);
                    }
                    // ajouter les evenements récemment ajoutés à l'ancienne valeur pour la date selectionnée :
                    evenementsPourDate.put(dateSelectionee, anciensEvenementsPourCetteDate
                        + "=> " + evenement_champ.getText() + "\n"); // Avant chaque évènement il y'a "=>", et aprés : retour à la ligne!

                // Mettre à jour le TextView :
                contenuEvenements.setText(evenementsPourDate.get(dateSelectionee));
                }
            }
        });
    }
}
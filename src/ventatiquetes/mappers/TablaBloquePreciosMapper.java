package ventatiquetes.mappers;

import ventatiquetes.models.Bloque;

import javax.swing.*;
import java.util.ArrayList;

public class TablaBloquePreciosMapper {
    public static void mapRows(ArrayList<Bloque> bloques, JComboBox combo)
    {

        for (int i=0 ; i<bloques.size();i++)
        {
            combo.addItem(bloques.get(i));

        }
        return ;
    }
}

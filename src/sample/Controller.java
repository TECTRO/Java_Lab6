package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.swing.text.View;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Controller implements Observer {
    public Model model = new Model(this);

    public TextArea ArrayViewArea;
    public TextArea RawArrayInputArea;
    public ListView ResultViewArea;
    public Label ResultItemsCountInfo;

    @Override
    public void notification(Object[] args) {
        try {
            switch (args[0].toString())
            {
                case "Numbers": {
                    List<Double> d = (List<Double>)args[1];
                    ArrayViewArea.setText(d.stream().map(Object::toString).collect(Collectors.joining("; ")));
                } break;

                case "SelectedNumbers":{
                    ResultViewArea.getItems().clear();
                    ResultViewArea.getItems().addAll((List<Double>)args[1]);
                    ResultItemsCountInfo.setText(String.valueOf(((List<Double>)args[1]).size()));
                } break;
            }
        }catch (Exception ignored){}
    }

    public void OnAdd(MouseEvent mouseEvent)
    {
        model.AddNumbers(RawArrayInputArea.getText());
        model.CalculateSelectedNumbers();
    }

    public void OnClear(MouseEvent mouseEvent)
    {
        model.ClearNumbers();
        model.CalculateSelectedNumbers();
    }

    public void OnRemove(MouseEvent mouseEvent)
    {
        model.RemoveNumbers(RawArrayInputArea.getText());
        model.CalculateSelectedNumbers();
    }

    public void OnReplace(MouseEvent mouseEvent) {
        model.ReplaceNumbers(RawArrayInputArea.getText());
        model.CalculateSelectedNumbers();
    }
}


package sample;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {

    private ArrayList<Double> Numbers = new ArrayList<>();
    private ArrayList<Double> SelectedNumbers = new ArrayList<>();
    private final ArrayList<Observer> observers = new ArrayList<>();

    public Model(Observer observer) {
        registerObserver(observer);
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void notifyObservers(Object[] args) {
        observers.forEach(obs->obs.notification(args));
    }


    public void AddNumbers(String RawNumbers) {
        Numbers.addAll(ParseNumbers(RawNumbers));
        NotifyAboutNumbers();
    }

    public void ReplaceNumbers(String RawNumbers) {
        Numbers = (ArrayList<Double>) ParseNumbers(RawNumbers);
        NotifyAboutNumbers();

    }

    public void RemoveNumbers(String RawNumbers) {
        Numbers.removeAll(ParseNumbers(RawNumbers));
        NotifyAboutNumbers();
    }

    public void ClearNumbers() {
        Numbers.clear();
        NotifyAboutNumbers();
    }

    public void CalculateSelectedNumbers() {
        SelectedNumbers = (ArrayList<Double>) GetSelectedNumbers();

        notifyObservers(new Object[]
                {
                        "SelectedNumbers",
                        SelectedNumbers
                });
    }

    private void NotifyAboutNumbers()
    {
        notifyObservers(new Object[]
                {
                        "Numbers",
                        Numbers
                });
    }

    private List<Double> GetSelectedNumbers() {
        List<Double> result = new ArrayList<>();
        if (Numbers.size() > 0)
            for (int i = 0; i < Numbers.size(); i++) {
                if (i == 0 && i == Numbers.size() - 1)
                    break;

                if (i == 0) {
                    if (Numbers.get(i) < Numbers.get(i + 1))
                        result.add(Numbers.get(i));
                } else if (i == Numbers.size() - 1) {
                    if (Numbers.get(i) < Numbers.get(i - 1))
                        result.add(Numbers.get(i));
                } else if ((Numbers.get(i) < Numbers.get(i + 1)) && (Numbers.get(i) < Numbers.get(i - 1)))
                    result.add(Numbers.get(i));
            }

        return result;
    }

    private List<Double> ParseNumbers(String Numbers) {
        ArrayList<Double> result = new ArrayList<>();

        StringBuilder rawNumbers = new StringBuilder(Numbers);

        for (int i = 0; i < rawNumbers.length(); i++) {
            char charAt = rawNumbers.charAt(i);
            if (charAt == ',')
                rawNumbers.replace(i, i + 1, ".");
        }

        for (int i = 0; i < rawNumbers.length(); i++) {
            char charAt = rawNumbers.charAt(i);
            if (!Character.isDigit(rawNumbers.charAt(i)))
                if (charAt != ' ' && charAt != '.') {
                    if (Character.isLetter(charAt)) {
                        rawNumbers.deleteCharAt(i);
                        i--;
                    } else
                        rawNumbers.replace(i, i + 1, " ");
                }
        }
        int sepCounter = 0;
        for (int i = 0; i < rawNumbers.length(); i++) {
            if (rawNumbers.charAt(i) == '.') {
                sepCounter++;

                if (sepCounter == 1) {
                    if (i == 0) {
                        rawNumbers.insert(0, '0');
                        i++;
                    } else if (rawNumbers.charAt(i - 1) == ' ') {
                        rawNumbers.insert(i, '0');
                        i++;
                    } else if (rawNumbers.charAt(i - 1) == '.') {
                        rawNumbers.deleteCharAt(i);
                        sepCounter--;
                        i--;
                    }

                    if (i == rawNumbers.length() - 1) {
                        rawNumbers.deleteCharAt(i);
                    } else if (rawNumbers.charAt(i + 1) == ' ') {
                        rawNumbers.insert(i + 1, '0');
                        i++;
                    } else if (rawNumbers.charAt(i + 1) == '.') {
                        rawNumbers.deleteCharAt(i);
                        sepCounter--;
                        i--;
                    }
                }
            }
            if (sepCounter > 1) {
                rawNumbers.replace(i, i + 1, " ");
                sepCounter = 0;
            }

            if (rawNumbers.charAt(i) == ' ')
                sepCounter = 0;
        }
        String[] split = rawNumbers.toString().split(" ");

        for (String cur : split)
            try {
                if (!cur.isEmpty())
                    result.add(Double.valueOf(cur));
            } catch (Exception e) {
                System.out.print(cur);
            }

        return result;
    }
}

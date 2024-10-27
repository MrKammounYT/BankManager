package me.kammoun.utils.TextField;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.ArrayList;

public class BJTextFieldWithAutoComplete extends JTextField {
    private List<String> suggestions;

    public BJTextFieldWithAutoComplete() {
        super();
        suggestions = new ArrayList<>();

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    private void updateSuggestions() {
        String input = getText();
        if (input.isEmpty()) {
            return;
        }

        for (String suggestion : suggestions) {
            if (suggestion.startsWith(input)) {
                setText(suggestion);
                setSelectionStart(input.length());
                setSelectionEnd(suggestion.length());
                break;
            }
        }
    }
}


package user.example.com;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PreferenciesFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencies);

        final EditTextPreference fragmentos = (EditTextPreference) findPreference(getResources().getString(R.string.pa3_key));

        fragmentos.setSummary(getString(R.string.pa3_summary, fragmentos.getText()));

        fragmentos.setOnPreferenceChangeListener((preference, newValue) -> {
            int valor;
            try{
                valor = Integer.parseInt((String)newValue);
            } catch (Exception e){
                Toast.makeText(getActivity(), R.string.pa3_error1, Toast.LENGTH_SHORT).show();
                return false;
            }
            if(valor >= 0 && valor <= 9){
                fragmentos.setSummary(getString(R.string.pa3_summary, "" + valor));
                return true;
            } else {
                Toast.makeText(getActivity(), R.string.pa3_error2, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

}

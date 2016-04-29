package com.project.sean.androidpos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.Database.EmpInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A DialogFragment to display the list of employees taken form the devices
 * database.
 * Created by Sean on 24/04/2016.
 */
public class EmployeeDialogFragment extends DialogFragment {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    //List of employee information
    ArrayList<EmpInfo> listItems = new ArrayList<EmpInfo>();

    //ListView to display employee information
    ListView mylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_emp, null, false);

        mylist = (ListView) view.findViewById(R.id.empList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(getActivity());

        //Add employee information from the DB to the list
        listItems.addAll(dbHelper.getAllEmpInfo());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        EmployeeAdapter adapter = new EmployeeAdapter(getActivity(), R.layout.dialog_row_emp, listItems);

        mylist.setAdapter(adapter);
    }

    public class EmployeeAdapter extends ArrayAdapter<EmpInfo> {
        private ArrayList<EmpInfo> empInfoArrayList;

        public EmployeeAdapter(Context context, int resource, List<EmpInfo> empInfoList) {
            super(context, resource, empInfoList);
            this.empInfoArrayList = new ArrayList<EmpInfo>();
            this.empInfoArrayList.addAll(empInfoList);
        }

        private class EmpViewHolder {
            TextView diEmpId;
            TextView diEmpFName;
            TextView diEmpLName;
            TextView diEmpRole;
            TextView diContact;
            TextView diPassword;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            EmpViewHolder holder = null;

            if(convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.dialog_row_emp, null);

                holder = new EmpViewHolder();
                holder.diEmpId = (TextView) convertView.findViewById(R.id.diEmpId);
                holder.diEmpFName = (TextView) convertView.findViewById(R.id.diEmpFName);
                holder.diEmpLName = (TextView) convertView.findViewById(R.id.diEmpLName);
                holder.diEmpRole = (TextView) convertView.findViewById(R.id.diEmpRole);
                holder.diContact = (TextView) convertView.findViewById(R.id.diContact);
                holder.diPassword = (TextView) convertView.findViewById(R.id.diPassword);

                convertView.setTag(holder);
            } else {
                holder = (EmpViewHolder) convertView.getTag();
            }

            EmpInfo empInfo = empInfoArrayList.get(position);
            holder.diEmpId.setText(Integer.toString(empInfo.getEmpId()));
            holder.diEmpFName.setText(empInfo.getEmpFName());
            holder.diEmpLName.setText(empInfo.getEmpLName());
            holder.diEmpRole.setText(empInfo.getRole());
            holder.diContact.setText(empInfo.getContactNo());
            holder.diPassword.setText(empInfo.getPassword());

            return convertView;
        }

    }

}

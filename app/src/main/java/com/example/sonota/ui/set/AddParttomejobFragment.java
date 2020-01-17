package com.example.sonota.ui.set;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddParttomejobFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddParttomejobFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 */


public class AddParttomejobFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText name;
    EditText hwage;
    EditText pday;
    EditText cday;
    private Button button;

    private int selected;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button button4;
    private OnFragmentInteractionListener mListener;

    public AddParttomejobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddParttomejobFragment.
     */

    private SonotaDBOpenHelper helper;
    private SQLiteDatabase db;


    // TODO: Rename and change types and number of parameters
    public static AddParttomejobFragment newInstance(String param1, String param2) {
        AddParttomejobFragment fragment = new AddParttomejobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_parttomejob, container, false);
        super.onCreate(savedInstanceState);
        Button button = root.findViewById(R.id.button4);
        name = root.findViewById(R.id.et_set_name);
        hwage = root.findViewById(R.id.et_set_hwage);
        pday = root.findViewById(R.id.et_set_pday);
        cday = root.findViewById(R.id.et_set_cday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobName = name.getText().toString();
                int jobhwage = Integer.valueOf(hwage.getText().toString()) ;
                int jobpday =  Integer.valueOf(pday.getText().toString());
                int jobcday = Integer.valueOf(cday.getText().toString());

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }
                if(db == null){
                    db = helper.getWritableDatabase();
                }

                insertData(db,jobName,jobhwage,jobpday,jobcday);
                Toast.makeText(getContext(),"完了しました！" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("SetParttimejobList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){

            name = root.findViewById(R.id.et_set_name);
            hwage = root.findViewById(R.id.et_set_hwage);
            pday = root.findViewById(R.id.et_set_pday);
            cday = root.findViewById(R.id.et_set_cday);

            selected = args.getInt("selected");

            name.setText(args.getString("Name"));
            hwage.setText(String.valueOf(args.getInt("Hwage")));
            pday.setText(args.getString("Pday"));
            cday.setText(args.getString("Cday"));
            isNewItem = false;
        }

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }

   // @Override
    //public void onAttach(Context context) {
      //  super.onAttach(context);
        //if (context instanceof OnFragmentInteractionListener) {
          //  mListener = (OnFragmentInteractionListener) context;
        //} else {
            //throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
        //}
    //}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void insertData(SQLiteDatabase db,String name,int hwage,int pday,int cday){
        ContentValues values = new ContentValues();
        values.put("byteahead_name",name);
        values.put("byteahead_hwage",hwage);
        values.put("byteahead_pday",pday);
        values.put("byteahead_cday",cday);

        db.insert("t_byteahead",null,values);
    }

    private void updata(SQLiteDatabase db,String name,int hwage,int pday,int cday){
        ContentValues values = new ContentValues();
        values.put("byteahead_name",name);
        values.put("byteahead_hwage",hwage);
        values.put("byteahead_pday",pday);
        values.put("byteahead_cday",cday);

        String[] whereArgs = {String.valueOf(selected)};

        db.update("t_byteahead",values, "byteahead_code=?",whereArgs);
    }
}

package com.example.arezoo.firealarm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arezoo.firealarm.Data.Address;
import com.example.arezoo.firealarm.Data.AddressManager;

/**
 * Created by Arezoo on 07-Sep-18.
 */

public class AddressAdapter extends RecyclerView.Adapter {
    AddressManager m;

    public AddressAdapter(Context context) {
        m = AddressManager.getInstance(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_row, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Address address = m.getAddresses().get(position);

        ((MyViewHolder) holder).id.setText(address.getId());
        ((MyViewHolder) holder).address.setText(address.getAddress());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Delete");

                alertDialog.setMessage("Are you sure you want delete this address?");


                alertDialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        m.deleteAddressAndReports(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Selected address deleted", Toast.LENGTH_SHORT).show();
                    }
                });


                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(v.getContext(), "You clicked on cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent myIntent = new Intent(v.getContext(), ReportActivity.class);
                String id = address.getId();
                myIntent.putExtra("addressID", id);
                v.getContext().startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
//        return m.getToDos().length;
        return m.getAddresses().size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, address;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            address = (TextView) view.findViewById(R.id.address);

        }
    }


}

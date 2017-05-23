package br.com.timberforest.pcp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity implements Animation.AnimationListener {

    private RecyclerView mPcpList;
    private DatabaseReference mDatabase;
    private DatabaseReference mMensagem;
    Animation animMove, recyclerV;
    private TextView txt_msg;

    private LinearLayout layout_principal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        layout_acidentes = (LinearLayout) findViewById(R.id.layout_acidentes);
        layout_principal = (LinearLayout) findViewById(R.id.layout_principal);


        txt_msg = (TextView) findViewById(R.id.txt_msg);

        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        // set animation listener
        animMove.setAnimationListener(this);

        txt_msg.setAnimation(animMove);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("pcp");
        mMensagem = FirebaseDatabase.getInstance().getReference().child("msg").child("msg_padrao");

        mPcpList = (RecyclerView) findViewById(R.id.list_pcp);
        mPcpList.setHasFixedSize(false);
        mPcpList.setLayoutManager(new LinearLayoutManager(this));


        recyclerV = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_recycler);
        recyclerV.setAnimationListener(this);

        mPcpList.setAnimation(recyclerV);





        mMensagem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mensagem = null;
                mensagem = dataSnapshot.getValue().toString();
                txt_msg.setText(mensagem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*        final long time = 2000;
        Timer timer = new Timer();
        TimerTask alternaTela = new TimerTask() {
            @Override
            public void run() {
                try{
                    layout_principal.setVisibility(View.GONE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(alternaTela, time, time);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Pcp, PcpViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pcp, PcpViewHolder>(
                Pcp.class,
                R.layout.pcp_row,
                PcpViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(PcpViewHolder viewHolder, Pcp model, int position) {
                viewHolder.setOrdemServico(model.getOrdemServico());
                viewHolder.setMaquina(model.getMaquina());
                viewHolder.setCliente(model.getCliente());
                viewHolder.setDtChegada(model.getDtChegada());
                viewHolder.setDtSaida(model.getDtSaida());
            }
        };
        mPcpList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    public static class PcpViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PcpViewHolder(View itemView) throws ParseException {
            super(itemView);
            mView = itemView;
        }

        public void setOrdemServico(String ordemServico) {
            TextView osTxt = (TextView) mView.findViewById(R.id.txt_os);
            osTxt.setText(ordemServico);
        }

        public void setMaquina(String maquina) {
            TextView maquinaTxt = (TextView) mView.findViewById(R.id.txt_maquina);
            maquinaTxt.setText(maquina);
        }


        public void setCliente(String cliente) {
            TextView clienteTxt = (TextView) mView.findViewById(R.id.txt_cliente);
            clienteTxt.setText(cliente);
        }


        public void setDtChegada(String dtChegada) {
            TextView dtChegadaTxt = (TextView) mView.findViewById(R.id.txt_dtChegada);
            dtChegadaTxt.setText(dtChegada);
        }

        public void setDtSaida(String dtSaida) {
            TextView dtSaidaTxt = (TextView) mView.findViewById(R.id.txt_dtPrevisao);
            dtSaidaTxt.setText(dtSaida);

            try {
                //recupera hora atual
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date hora = Calendar.getInstance().getTime();
                final String dataFormatada = sdf.format(hora);
                String saida = dtSaidaTxt.getText().toString();
                saida = saida.replace("-", "/");


                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                df.setLenient(false);
                Date d1 = df.parse(dataFormatada);
                Date d2 = df.parse(saida);
                long dt = (d2.getTime() - d1.getTime()) + 3600000;
                dt = (dt / 86400000L);
                String result = String.valueOf(dt);
                if (dt > 6) {
                    TextView dtPrevisaoTxt = (TextView) mView.findViewById(R.id.txt_restantes);
                    dtPrevisaoTxt.setText(result + " dias");
                    dtPrevisaoTxt.setBackgroundResource(R.drawable.screen_border_botao_grande_verde);
                } else if (dt <= 6 && dt > 3) {
                    TextView dtPrevisaoTxt = (TextView) mView.findViewById(R.id.txt_restantes);
                    dtPrevisaoTxt.setText(result + " dias");
                    dtPrevisaoTxt.setBackgroundResource(R.drawable.screen_border_botao_grande_amarelo);
                } else if (dt <= 3) {
                    TextView dtPrevisaoTxt = (TextView) mView.findViewById(R.id.txt_restantes);
                    dtPrevisaoTxt.setText(result + " dias");
                    dtPrevisaoTxt.setBackgroundResource(R.drawable.screen_border_botao_grande_vermelho);
                }

                TextView dtPrevisaoTxt = (TextView) mView.findViewById(R.id.txt_restantes);
                dtPrevisaoTxt.setText(result + " dias");

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
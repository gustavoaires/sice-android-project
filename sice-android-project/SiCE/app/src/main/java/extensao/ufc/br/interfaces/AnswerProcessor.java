package extensao.ufc.br.interfaces;

import extensao.ufc.br.network.Answer;

/**
 * Created by alan on 11/22/15.
 */
public abstract class AnswerProcessor {
    Object answer;

    public AnswerProcessor(Object answer){
        this.answer = answer;
    }

    public void run() {
        if(answer == null){
            onRequestFailure();
        }else{
            Answer answer = (Answer) this.answer;
            switch(answer.getResult()){

                case SUCCESS:
                    onSuccess(answer);
                    break;

                case ERROR:
                    onError(answer);
                    break;

                case INTERNAL_ERROR:
                    onInternalError(answer);
                    break;

            }
        }
    }

    public abstract void onInternalError(Answer answer);
    public abstract void onError(Answer answer);
    public abstract void onSuccess(Answer answer);
    public abstract void onRequestFailure();
}

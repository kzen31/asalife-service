package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.TaskMess;
import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskMess;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class TaskMessMapper {
    private final ModelMapper modelMapper;

    public SetTaskMess entityToSetTaskMess(TaskMess taskMess) {
        SetTaskMess setTaskMess = new SetTaskMess();

        setTaskMess.setCreatedat(taskMess.getCreatedAt());
        setTaskMess.setMess(taskMess.getMess());

        setTaskMess.setRuangtvkacajendelakusen(taskMess.getRuangTvKacaJendelaKusen());
        setTaskMess.setRuangtvcermin(taskMess.getRuangTvCermin());
        setTaskMess.setRuangtvdispenser(taskMess.getRuangTvDispenser());
        setTaskMess.setRuangtvac(taskMess.getRuangTvAc());
        setTaskMess.setRuangtvfurniture(taskMess.getRuangTvFurniture());
        setTaskMess.setRuangtvraktv(taskMess.getRuangTvRakTv());
        setTaskMess.setRuangtvtiraikarpet(taskMess.getRuangTvTiraiKarpet());
        setTaskMess.setRuangtvdinding(taskMess.getRuangTvDinding());
        setTaskMess.setRuangtvlantai(taskMess.getRuangTvLantai());

        setTaskMess.setKoridortempatsampah(taskMess.getKoridorTempatSampah());
        setTaskMess.setKoridorpintu(taskMess.getKoridorPintu());
        setTaskMess.setKoridorlantaisudutlantai(taskMess.getKoridorLantaiSudutLantai());
        setTaskMess.setKoridorkeset(taskMess.getKoridorKeset());
        setTaskMess.setKoridorpantry(taskMess.getKoridorPantry());
        setTaskMess.setKoridorwastafelchromefixture(taskMess.getKoridorWastafelChromeFixture());
        setTaskMess.setKoridorperalatanmakanrakpiring(taskMess.getKoridorPeralatanMakanRakPiring());
        setTaskMess.setKoridorpintudinding(taskMess.getKoridorPintuDinding());
        setTaskMess.setKoridorkancajendelakusen(taskMess.getKoridorKancaJendelaKusen());

        setTaskMess.setToiletpintudinding(taskMess.getToiletPintuDinding());
        setTaskMess.setToilettempatsampah(taskMess.getToiletTempatSampah());
        setTaskMess.setToiletwastafelchromefixture(taskMess.getToiletWastafelChromeFixture());
        setTaskMess.setToileturinoirselangtoiletbowl(taskMess.getToiletUrinoirSelangToiletBowl());
        setTaskMess.setToiletshowerareacurtain(taskMess.getToiletShowerAreaCurtain());
        setTaskMess.setToiletlantaisudutlantai(taskMess.getToiletLantaiSudutLantai());
        setTaskMess.setToiletteras(taskMess.getToiletTeras());
        return setTaskMess;
    }

    public List<SetTaskMess> mapToSetTaskMessList (List<TaskMess> taskMessList) {
        List<SetTaskMess> setTaskMessList = new ArrayList<>();

        for (TaskMess taskMess : taskMessList) { // (int i = 0; i < list.size(); i++)
            SetTaskMess setTaskMess = entityToSetTaskMess(taskMess);
            setTaskMessList.add(setTaskMess);
        }
        return setTaskMessList;
    }
}

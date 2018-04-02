package com.example.dishfo.androidapp.util.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.TableConstant;

import java.util.Set;

/**
 * Created by dishfo on 18-2-28.
 */
//用于保存　专区　对应的　帖子表名　回复表名
public class AreaWithNDMapping {

    public static final AreaWithNDMapping INSTANCE=new AreaWithNDMapping();

    private ArrayMap<String,String> noteMaps;
    private ArrayMap<String,String> discussMaps;

    public AreaWithNDMapping() {
        this.noteMaps = new ArrayMap<>();
        this.discussMaps = new ArrayMap<>();
        noteMaps.put("开发团队", TableConstant.Note);
        discussMaps.put("开发团队",TableConstant.discuss);

        noteMaps.put("学习天堂",TableConstant.notestudy);
        discussMaps.put("学习天堂",TableConstant.discussstudy);

        noteMaps.put("运动广场",TableConstant.notesport);
        discussMaps.put("运动广场",TableConstant.discusssport);

        noteMaps.put("旅游天地",TableConstant.notetour);
        discussMaps.put("旅游天地",TableConstant.discusstour);

        noteMaps.put("游戏",TableConstant.notegame);
        discussMaps.put("游戏",TableConstant.discussgame);

        noteMaps.put("娱乐",TableConstant.noteentertainment);
        discussMaps.put("娱乐",TableConstant.discussentertainment);

        noteMaps.put("明星",TableConstant.notestar);
        discussMaps.put("明星",TableConstant.discussstar);

        noteMaps.put("阅读",TableConstant.noteread);
        discussMaps.put("阅读",TableConstant.discuessread);

        noteMaps.put("美食",TableConstant.noteate);
        discussMaps.put("美食",TableConstant.discusscate);

        noteMaps.put("动漫",TableConstant.notecomic);
        discussMaps.put("动漫",TableConstant.discusscomic);

    }

    public String getNote(String area){
        return noteMaps.get(area);
    }

    public String getDiscuss(String area){
        return  discussMaps.get(area);
    }

    public Set<String> getAreas(){
        return discussMaps.keySet();
    }


}

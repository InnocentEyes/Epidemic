package com.qzlnode.epidemic.miniprogram.pojo;

/**
 * @author qzlzzz
 */
public class CommentType {

    private Integer id;

    private Integer typeNo;

    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "CommentType{" +
                "id=" + id +
                ", typeNo=" + typeNo +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}

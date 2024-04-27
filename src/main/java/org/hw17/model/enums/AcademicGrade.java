package org.hw17.model.enums;

public enum AcademicGrade {

    ASSOCIATE(2),
    UNINTERRUPTED_BACHELOR(4),
    INTERRUPTED_BACHELOR(4),
    UNINTERRUPTED_MASTERS(6),
    INTERRUPTED_MASTERS(2),
    UNINTERRUPTED_DOCTORATE(7),
    INTERRUPTED_DOCTORATE(5),
    PROFESSIONAL_DOCTORATE(5);

    private int graduateDuration;

    AcademicGrade(int graduateDuration){
        this.graduateDuration = graduateDuration;
    }

    public int getGraduateDuration(){
        return this.graduateDuration;
    }

}

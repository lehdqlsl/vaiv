package com.test.vaiv.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.vaiv.domain.Press;
import com.test.vaiv.domain.QPress;
import com.test.vaiv.domain.QSpeaker;
import com.test.vaiv.dto.PressDto;
import com.test.vaiv.dto.QPressDto;
import com.test.vaiv.dto.SearchDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PressQueryRepository extends QuerydslRepositorySupport {

    public PressQueryRepository() {
        super(Press.class);
    }

    public List<PressDto> findPressByOption(SearchDto searchDto) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(super.getEntityManager());
        QPress qPress = QPress.press;
        QSpeaker qSpeaker = QSpeaker.speaker;

        BooleanBuilder builder = new BooleanBuilder();

        JPAQuery<PressDto> ret = queryFactory.select(new QPressDto(qPress.date, qPress.title));

        if (searchDto.getStartDate() != null) {
            builder.and(qPress.date.goe(searchDto.getStartDate()));
        }

        if (searchDto.getEndDate() != null) {
            builder.and(qPress.date.loe(searchDto.getEndDate()));
        }

        if (!searchDto.getName().isEmpty()) {
            builder.and(qSpeaker.name.eq(searchDto.getName()));
        }

        if (!searchDto.getParty().isEmpty()) {
            builder.and(qSpeaker.party.eq(searchDto.getParty()));
        }

        if (searchDto.getName().isEmpty() && searchDto.getParty().isEmpty()) {
            ret.from(qPress).where(builder);
        } else {
            ret.from(qPress)
                    .rightJoin(qSpeaker)
                    .on(qPress.id.eq(qSpeaker.press.id))
                    .where(builder);
        }
        ret.orderBy(qPress.date.desc()).limit(100);
        return ret.fetch();
    }

}

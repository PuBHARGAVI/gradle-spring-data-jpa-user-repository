package com.hxs.web.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Centralize all the projection conversion stuff.
 *
 *  Yes, I know you can auto-create/return projections at the Spring Data
 *  repository level but I feel like the DAO layer should just deal in terms of Entities while the Controller/"View"
 *  layer can take care of fulfilling the API contract. It could be argued that Projections at the data layer result
 *  in less data being passed around in the app but then you are stuck with duplicate methods all returning different
 *  projection types and now your whole app is coupled to projections.
 *
 *  I just opt to go this way. At least I'm using generics here to help and keep things DRYer.
 *
 * @author hsteidel
 */
@Component
public class ProjectionUtils {

    private final ProjectionFactory projectionFactory;

    public ProjectionUtils(ProjectionFactory projectionFactory) {
        this.projectionFactory = projectionFactory;
    }

    public <T> List<T> toProjectionList(Class<T> clazz, Set<?> set){
        return toProjectionList(clazz, new ArrayList<>(set));
    }

    public <T> List<T> toProjectionList(Class<T> clazz, List<?> list){
        return list.stream().map(o -> projectionFactory.createProjection(clazz, o)).collect(Collectors.toList());
    }

    public <T> T toProjection(Class<T> clazz, Object entity){
        return projectionFactory.createProjection(clazz, entity);
    }

    public <R> PagedResources<Resource<R>> toProjectionPageResource(Class<R> resourceClazz, Page page,
                                                                    Pageable pageable, PagedResourcesAssembler<R> assembler){
        List<R> summaries = toProjectionList(resourceClazz, page.getContent());
        Page<R> summariesPage = new PageImpl<>(summaries, pageable, page.getTotalElements());
        return assembler.toResource(summariesPage);

    }
}

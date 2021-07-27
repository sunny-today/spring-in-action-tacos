package tacos.web.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import tacos.Taco;

import java.util.ArrayList;
import java.util.List;

/**
 * 매개변수로 받은 객체를 RepresentationModel로 변환
 */
@Component
public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

    public TacoResourceAssembler(){
        super(DesignTacoController.class, TacoResource.class);
    }

    /**
     * entity(taco)를 resource 객체로 변환
     */
    @Override
    protected TacoResource instantiateModel(Taco taco) {
        return new TacoResource(taco);
    }

    /**
     * entity의 id로 링크를 만들고, resource 객체로 반환
     * EX) id 1 -> design/1
     */
    @Override
    public TacoResource toModel(Taco taco) {
        return createModelWithId(taco.getId(),taco);
    }

    /**
     * entity 여러개를 한번에 변환하여 CollectionModel로 반환
     */
    @Override
    public CollectionModel<TacoResource> toCollectionModel(Iterable<? extends Taco> tacos) {
        List<TacoResource> postList = new ArrayList<>();

        for(Taco taco : tacos)
            postList.add(toModel(taco));

        return super.toCollectionModel(tacos);
    }

}

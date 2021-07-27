//tag::recents[]
package tacos.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//end::recents[]
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path="/design",produces="application/json")
@CrossOrigin(origins="*")   // cors - 서로 다른 도메인 간의 요청 허용
public class DesignTacoController {

    @Autowired
    private TacoRepository tacoRepo;

    @Autowired
    private  TacoResourceAssembler tacoResourceAssembler;

//    @GetMapping("/recent")
//    public Iterable<Taco> recentTacos() {
//      PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//      return tacoRepo.findAll(page).getContent();
//    }

    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);

        // recents 속성의 링크 생성
        // WebMvcLinkBuilder 를 사용하여 하드코딩된 link 제거
        recentResources.add(
                WebMvcLinkBuilder.linkTo(DesignTacoController.class)
                .slash("recent")   // URL에 인자와 값 추가 - /design/recents
                .withRel("recents") // 해당 link의 관계 이름 (링크 참조시 사용)
        );

        return recentResources;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public CollectionModel<TacoResource> recentTacos2() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();

        CollectionModel<TacoResource> recentResources = tacoResourceAssembler.toCollectionModel(tacos);

        recentResources.add(
                WebMvcLinkBuilder.linkTo(DesignTacoController.class)
                        .withSelfRel()
        );

        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
          return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        // null 이면 정상 처리 HTTP 200(OK)가 아닌 404(NOT FOUND) 상태 코드를 응답으로 반환하도록
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201(CREATED)
    public Taco postTaco(@RequestBody Taco taco) {  // 요청 몸체의 JSON 데이터가 Taco객체로 변환되어 taco 매개변수와 바인딩됨.
        return tacoRepo.save(taco);
    }
}



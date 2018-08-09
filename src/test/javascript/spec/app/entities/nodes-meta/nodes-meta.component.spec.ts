/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesMetaComponent } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.component';
import { NodesMetaService } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.service';
import { NodesMeta } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.model';

describe('Component Tests', () => {

    describe('NodesMeta Management Component', () => {
        let comp: NodesMetaComponent;
        let fixture: ComponentFixture<NodesMetaComponent>;
        let service: NodesMetaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesMetaComponent],
                providers: [
                    NodesMetaService
                ]
            })
            .overrideTemplate(NodesMetaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesMetaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesMetaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NodesMeta(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nodesMetas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

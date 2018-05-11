/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeMetaRegistryDetailComponent } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry-detail.component';
import { NodeMetaRegistryService } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.service';
import { NodeMetaRegistry } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.model';

describe('Component Tests', () => {

    describe('NodeMetaRegistry Management Detail Component', () => {
        let comp: NodeMetaRegistryDetailComponent;
        let fixture: ComponentFixture<NodeMetaRegistryDetailComponent>;
        let service: NodeMetaRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeMetaRegistryDetailComponent],
                providers: [
                    NodeMetaRegistryService
                ]
            })
            .overrideTemplate(NodeMetaRegistryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeMetaRegistryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeMetaRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NodeMetaRegistry(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nodeMetaRegistry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

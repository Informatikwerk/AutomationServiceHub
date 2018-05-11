/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeMetaRegistryComponent } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.component';
import { NodeMetaRegistryService } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.service';
import { NodeMetaRegistry } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.model';

describe('Component Tests', () => {

    describe('NodeMetaRegistry Management Component', () => {
        let comp: NodeMetaRegistryComponent;
        let fixture: ComponentFixture<NodeMetaRegistryComponent>;
        let service: NodeMetaRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeMetaRegistryComponent],
                providers: [
                    NodeMetaRegistryService
                ]
            })
            .overrideTemplate(NodeMetaRegistryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeMetaRegistryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeMetaRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NodeMetaRegistry(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nodeMetaRegistries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

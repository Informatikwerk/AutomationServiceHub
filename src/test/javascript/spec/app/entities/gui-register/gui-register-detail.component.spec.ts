/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { GuiRegisterDetailComponent } from '../../../../../../main/webapp/app/entities/gui-register/gui-register-detail.component';
import { GuiRegisterService } from '../../../../../../main/webapp/app/entities/gui-register/gui-register.service';
import { GuiRegister } from '../../../../../../main/webapp/app/entities/gui-register/gui-register.model';

describe('Component Tests', () => {

    describe('GuiRegister Management Detail Component', () => {
        let comp: GuiRegisterDetailComponent;
        let fixture: ComponentFixture<GuiRegisterDetailComponent>;
        let service: GuiRegisterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [GuiRegisterDetailComponent],
                providers: [
                    GuiRegisterService
                ]
            })
            .overrideTemplate(GuiRegisterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GuiRegisterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuiRegisterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GuiRegister(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.guiRegister).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

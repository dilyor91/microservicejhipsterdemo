import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RegInstructuionFormService } from './reg-instructuion-form.service';
import { RegInstructuionService } from '../service/reg-instructuion.service';
import { IRegInstructuion } from '../reg-instructuion.model';

import { RegInstructuionUpdateComponent } from './reg-instructuion-update.component';

describe('RegInstructuion Management Update Component', () => {
  let comp: RegInstructuionUpdateComponent;
  let fixture: ComponentFixture<RegInstructuionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let regInstructuionFormService: RegInstructuionFormService;
  let regInstructuionService: RegInstructuionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RegInstructuionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RegInstructuionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RegInstructuionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    regInstructuionFormService = TestBed.inject(RegInstructuionFormService);
    regInstructuionService = TestBed.inject(RegInstructuionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const regInstructuion: IRegInstructuion = { id: 456 };

      activatedRoute.data = of({ regInstructuion });
      comp.ngOnInit();

      expect(comp.regInstructuion).toEqual(regInstructuion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegInstructuion>>();
      const regInstructuion = { id: 123 };
      jest.spyOn(regInstructuionFormService, 'getRegInstructuion').mockReturnValue(regInstructuion);
      jest.spyOn(regInstructuionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regInstructuion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: regInstructuion }));
      saveSubject.complete();

      // THEN
      expect(regInstructuionFormService.getRegInstructuion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(regInstructuionService.update).toHaveBeenCalledWith(expect.objectContaining(regInstructuion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegInstructuion>>();
      const regInstructuion = { id: 123 };
      jest.spyOn(regInstructuionFormService, 'getRegInstructuion').mockReturnValue({ id: null });
      jest.spyOn(regInstructuionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regInstructuion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: regInstructuion }));
      saveSubject.complete();

      // THEN
      expect(regInstructuionFormService.getRegInstructuion).toHaveBeenCalled();
      expect(regInstructuionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegInstructuion>>();
      const regInstructuion = { id: 123 };
      jest.spyOn(regInstructuionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regInstructuion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(regInstructuionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

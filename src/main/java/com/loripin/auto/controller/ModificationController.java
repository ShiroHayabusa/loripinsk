package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.model.dto.SpotDto;
import com.loripin.auto.repos.FileStorage;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.loripin.auto.controller.SpotController.dateAndTime;

@Controller
public class ModificationController {
    private final
    ReplyService replyService;
    private final
    CommentService commentService;
    private final
    SpecService specService;
    private final
    GearBoxTypeService gearBoxTypeService;
    private final
    FuelService fuelService;
    private final
    EngineTypeService engineTypeService;
    private final
    BodyTypeService bodyTypeService;
    private final
    UserService userService;
    private final
    SpotService spotService;
    private final
    SeriesService seriesService;
    private final
    TunerService tunerService;
    private final
    GearBoxService gearBoxService;
    private final
    DriveService driveService;
    private final
    BodyService bodyService;
    private final
    EngineService engineService;
    private final
    FileStorage fileStorage;
    private final
    ModificationService modificationService;

    public ModificationController(ModificationService modificationService,
                                  FileStorage fileStorage,
                                  EngineService engineService,
                                  BodyService bodyService,
                                  DriveService driveService,
                                  GearBoxService gearBoxService,
                                  TunerService tunerService,
                                  SeriesService seriesService,
                                  SpotService spotService,
                                  UserService userService,
                                  BodyTypeService bodyTypeService,
                                  EngineTypeService engineTypeService,
                                  FuelService fuelService,
                                  GearBoxTypeService gearBoxTypeService,
                                  SpecService specService,
                                  CommentService commentService,
                                  ReplyService replyService) {
        this.modificationService = modificationService;
        this.fileStorage = fileStorage;
        this.engineService = engineService;
        this.bodyService = bodyService;
        this.driveService = driveService;
        this.gearBoxService = gearBoxService;
        this.tunerService = tunerService;
        this.seriesService = seriesService;
        this.spotService = spotService;
        this.userService = userService;
        this.bodyTypeService = bodyTypeService;
        this.engineTypeService = engineTypeService;
        this.fuelService = fuelService;
        this.gearBoxTypeService = gearBoxTypeService;
        this.specService = specService;
        this.commentService = commentService;
        this.replyService = replyService;
    }

    @Value("${upload.path}")
    private String uploadPath;


    public static String existingPhoto;
    public static List<String> existingPhotos;
    public static String resultFilename;


    @GetMapping("/modification")
    public void showModification(Model model) {
    }

    @GetMapping("/modificationCreate")
    public String createModificationForm(@AuthenticationPrincipal User user,
                                         Modification modification,
                                         Model model){

        List<Tuner> tuners = tunerService.findAllByOrderByName();
        model.addAttribute("tuners", tuners);

        List<Engine> engines = engineService.findAllByOrderByNameAsc();
        model.addAttribute("engines", engines);

        List<GearBox> gearBoxes = gearBoxService.findAllByOrderByNameAsc();
        model.addAttribute("gearBoxes", gearBoxes);

        List<Body> bodies = bodyService.findAllByOrderByNameAsc();
        model.addAttribute("bodies", bodies);

        List<Drive> drives = driveService.findAllByOrderByIdAsc();
        model.addAttribute("drives", drives);

        List<EngineType> engineTypes = engineTypeService.findAllByOrderByNameAsc();
        model.addAttribute("engineTypes", engineTypes);

        List<Fuel> fuels = fuelService.findAllByOrderByIdAsc();
        model.addAttribute("fuels", fuels);

        List<GearBoxType> gearBoxTypes = gearBoxTypeService.findAllByOrderByNameAsc();
        model.addAttribute("gearBoxTypes", gearBoxTypes);

        List<Spec> specs = specService.findAllByOrderByName();
        model.addAttribute("specs", specs);

        User user1 = userService.findById(user.getId());

        model.addAttribute("bodyType", bodyTypeService.findById(user1.getTmp()));

        List<Series> series = seriesService.findAll();
        model.addAttribute("series", series);

        return "modificationCreate";
    }

    @PostMapping("/modificationCreate")
    public String createModification(@AuthenticationPrincipal User user,
                                     Modification modification,
                                     Model model,
                                     @RequestParam("file") MultipartFile file1,
                                     @RequestParam("files") MultipartFile[] files
    ) throws IOException {

        uploadFile(modification, file1);
        modification.setPhoto(resultFilename);


        List<String> fileNames = uploadFiles(model, files);
        modification.setPhotos(fileNames);

        User user1 = userService.findById(user.getId());

        modification.setBodyType(bodyTypeService.findById(user1.getTmp()));
        modificationService.saveModification(modification);
        return "redirect:/catalog/manufacturer/carmodel/generation/restyle/" + user1.getTmp();
    }

    public void uploadFile(Modification modification,
                           @RequestParam("file1") MultipartFile file1
    ) throws IOException {
        if (file1 != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file1.getOriginalFilename();
            file1.transferTo(new File(uploadPath + "/" + resultFilename));
        }
    }

    public List<String> uploadFiles(Model model,
                                    @RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = null;
        try {
            fileNames = Arrays.asList(files)
                    .stream()
                    .map(file -> {
                        fileStorage.store(file);
                        return file.getOriginalFilename();
                    })
                    .collect(Collectors.toList());
            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
        return fileNames;
    }

    @GetMapping("modificationUpdate/{id}")
    public String updateModificationForm(@AuthenticationPrincipal User user,
                                         @PathVariable("id") Long id,
                                         Model model) {
        List<Tuner> tuners = tunerService.findAllByOrderByName();
        model.addAttribute("tuners", tuners);
        List<Engine> engines = engineService.findAllByOrderByIdAsc();
        model.addAttribute("engines", engines);
        List<GearBox> gearBoxes = gearBoxService.findAllByOrderByIdAsc();
        model.addAttribute("gearBoxes", gearBoxes);
        List<Body> bodies = bodyService.findAllByOrderByIdAsc();
        model.addAttribute("bodies", bodies);
        List<Drive> drives = driveService.findAllByOrderByIdAsc();
        model.addAttribute("drives", drives);
        List<Series> series = seriesService.findAll();
        model.addAttribute("series", series);
        List<EngineType> engineTypes = engineTypeService.findAllByOrderByNameAsc();
        model.addAttribute("engineTypes", engineTypes);
        List<Fuel> fuels = fuelService.findAllByOrderByIdAsc();
        model.addAttribute("fuels", fuels);
        List<GearBoxType> gearBoxTypes = gearBoxTypeService.findAllByOrderByNameAsc();
        model.addAttribute("gearBoxTypes", gearBoxTypes);
        List<Spec> specs = specService.findAllByOrderByName();
        model.addAttribute("specs", specs);

        Modification modification = modificationService.findById(id);
        model.addAttribute("modification", modification);

        user.setTmp(id);
        userService.save(user);

        existingPhoto = modification.getPhoto();
        existingPhotos = modification.getPhotos();
        return "modificationUpdate";
    }

    @PostMapping("/modificationUpdate")
    public String updateModification(@AuthenticationPrincipal User user,
                                     Modification modification,
                                     Model model,
                                     @RequestParam("file1") MultipartFile file1,
                                     @RequestParam("files") MultipartFile[] files)
            throws IOException {
        uploadFile(modification, file1);
        if (file1.isEmpty()) {
            modification.setPhoto(existingPhoto);
        } else {
            modification.setPhoto(resultFilename);
        }

        List<String> fileNames = uploadFiles(model, files);
        if (fileNames == null) {
            modification.setPhotos(existingPhotos);
        } else {
            modification.setPhotos(fileNames);
        }

        if (modification.getUniq() != null) {
            modification.getBodyType().getGeneration().getCarmodel().setUniq(true);
        } else {
            modification.getBodyType().getGeneration().getCarmodel().setUniq(null);
        }
        modificationService.saveModification(modification);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/generation/restyle/modification/" + user1.getTmp();
    }



    @GetMapping("/catalog/manufacturer/carmodel/generation/restyle/modification/{id}")
    public String carOpenForm(@AuthenticationPrincipal User user,
                              @PathVariable Long id,
                              Comment comment,
                              Model model) {
        Modification modification = modificationService.findById(id);
        model.addAttribute("modification", modification);

        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }

        List<Comment> comments = commentService.findByModificationIdOrderByIdAsc(id);
        model.addAttribute("comments", comments);

        List<Reply> replies = replyService.findByModificationIdOrderByIdAsc(id);
        model.addAttribute("replies",replies);

        List<SpotDto> spots = spotService.findByModificationIdOrderByIdDesc(modificationService.findById(id), user);
        model.addAttribute("spots", spots);
        return "modificationOpen";
    }

    @PostMapping("modification")
    public String carOpen(@AuthenticationPrincipal User user,
                          Comment comment) {
        User user1 = userService.findById(user.getId());
        comment.setModification(modificationService.findById(user1.getTmp()));
        comment.setUser(user1);
        comment.setDate(dateAndTime());
        commentService.save(comment);
        return "redirect:/catalog/manufacturer/carmodel/generation/restyle/modification/" + user1.getTmp();
    }

    @GetMapping("/restyles/car/modification/delete/{id}")
    public String deleteModification(@AuthenticationPrincipal User user,
                                     @PathVariable("id") Long id) {
        modificationService.deleteById(id);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/generation/restyle/" + user1.getTmp();
    }

}

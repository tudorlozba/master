package Client.Service;

/**
 * Created by tudor on 15/01/16.
 */

import Common.Service.SocialNetworkService;
import Common.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class SocialNetworkServiceClient implements SocialNetworkService {
    public static final String URL = "http://localhost:8080/artGallery";
    public static final String WORK = "works";
    public static final String ARTIST = "artists";
    static Log log = LogFactory.getLog(SocialNetworkServiceClient.class.getSimpleName());

    private User user;

    public SocialNetworkServiceClient() {

    }

//    public GalerieServiceClient(RpcClient rpcClient) {
//        this.rpcClient = rpcClient;
//    }
//
//
//    public void setWorks(OperaRepository works) {
//        this.operaRepository = works;
//    }
//
//
//    public void setArtists(ArtistRepository artists) {
//        this.artistRepository = artists;
//    }

   /* @Override
    public Artist addNewArtist(Artist artist) throws ValidationException {
        execute(() -> restTemplate.postForObject(String.format("%s/%s", URL, ARTIST), artist, Artist.class));
        log.info("Add new artist" + artist.getNume());
        return findByName(artist.getNume());

    }

    @Override
    public Opera addNewWork(Opera opera)
            throws ValidationException, InterruptedException, ExecutionException, ServiceException {
        execute(() -> restTemplate.postForObject(String.format("%s/%s", URL, WORK), opera, Opera.class));
        log.info("Add new work " + opera.getTitlu());
        return findByTitle(opera.getTitlu());
    }

    @Override
    public Page<OperaDto> viewWorks(Pageable pageable) throws ServiceException {
//        Future<Collection<Opera>> futureWorks = executorService.submit(() -> {
//            return operaRepository.findAll();
//        });
//        Future<Collection<Artist>> futureArtist = executorService.submit(() -> {
//            return artistRepository.findAll();
//        });
//        Page<OperaDto> page = new Page<>();
//        int pageSize = pageable.getPageSize();
//        int pageNumber = pageable.getPageNumber();
//        try {
//            Collection<Opera> works = futureWorks.get();
//            Collection<Artist> artists = futureArtist.get();
//            page.setPageNumber(pageNumber);
//            page.setPageSize(pageSize);
//            List<OperaDto> art = works.stream().sorted((n1, n2) -> n1.getTitlu().compareTo(n2.getTitlu()))
//                    .skip((pageNumber - 1) * pageSize).limit(pageSize).map((opera) -> {
//                        OperaDto operaDto = new OperaDto();
//                        operaDto.id = opera.getId();
//                        operaDto.tip = opera.getTip();
//                        operaDto.workName = opera.getTitlu();
//                        Optional<Artist> artistRes = artists.stream()
//                                .filter((artist) -> artist.getId() == opera.getIdArtist()).findFirst();
//                        if (artistRes.isPresent()) {
//                            operaDto.artistName = artistRes.get().getNume();
//                        }
//                        return operaDto;
//                    }).collect(Collectors.toList());
//            page.setNoOfElements(works.size());
//            page.setItems(art);
//            log.info("Returned object " + page.getClass().getSimpleName());
//            return page;
//        } catch (InterruptedException e) {
//            log.warn("Works loading interrupeted!", e);
//            return page;
//        } catch (ExecutionException e) {
//            log.error("An error", e);
//            throw new ServiceException(e);
//        }
        return null;
    }

    @Override
    public Page<Artist> viewArtists(Pageable pageable) throws ServiceException {
//        Page<Artist> page = new Page<>();
//        Message request = new Message.Builder()
//                .what(GalerieService.VIEW_ARTISTS)
//                .body(pageable.getPageNumber().toString() + "," + pageable.getPageSize().toString()).build();
//        Message response = rpcClient.sendAndReceive(request);
//        if (response.what().equals(Message.ERROR)) throw new ServiceException(response.body());
//        else if (response.what().equals(Message.OK)) {
//            String[] data = response.body().split(",");
////            System.out.println(data);
//            Integer pageNumber = Integer.valueOf(data[0]);
//            Integer pageSize = Integer.valueOf(data[1]);
//            Integer noOfElements = Integer.valueOf(data[2]);
//            int i = 3;
//            int length = data.length;
//            List<Artist> artists = new ArrayList<>();
//            while (i < length) {
//                Artist artist = new Artist();
//                artist.setId(Integer.valueOf(data[i]));
//                i++;
//                artist.setNume(data[i]);
//                i++;
//                artist.setAnN(Integer.valueOf(data[i]));
//                i++;
//                artist.setAnM(Integer.valueOf(data[i]));
//                i++;
//                artists.add(artist);
//            }
////            artists.forEach(System.out::println);
//            page.setItems(artists);
//            page.setNoOfElements(noOfElements);
//            page.setPageNumber(pageNumber);
//            page.setPageSize(pageSize);
//        }
//        return page;
        return null;
    }

    public ArrayList<Opera> viewWorkss() {
        Opera[] works = execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, WORK), Opera[].class));
        return new ArrayList<>(Arrays.asList(works));
    }

    public ArrayList<OperaDto> viewWorksList() {
        ArrayList<Opera> works = viewWorkss();
        ArrayList<Artist> artists = viewArtistss();
        return new ArrayList<>(works.stream().sorted((n1, n2) -> n1.getTitlu().compareTo(n2.getTitlu()))
                .map((opera) -> {
                    OperaDto operaDto = new OperaDto();
                    operaDto.id = opera.getIdOpera();
                    operaDto.tip = opera.getTip();
                    operaDto.workName = opera.getTitlu();
                    Optional<Artist> artistRes = artists.stream()
                            .filter((artist) -> artist.getIdArtist() == opera.getIdArtist()).findFirst();
                    if (artistRes.isPresent()) {
                        operaDto.artistName = artistRes.get().getNume();
                    }
                    return operaDto;
                }).collect(Collectors.toList()));
    }

    public ArrayList<Artist> viewArtistss() {
        Artist[] artists = execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, ARTIST), Artist[].class));
        return new ArrayList<>(Arrays.asList(artists));
    }

    @Override
    public Artist deleteArtist(Integer id) throws InterruptedException, ExecutionException {
//        Collection<Opera> allWorks = viewWorkss();
//        for (Opera op : allWorks) {
//            if (op.getIdArtist() == id) {
//                log.warn("Delete first artist with id " + id);
//                throw new ServiceException("Delete first artist with id " + id);
//            }
//        }
        return execute(() -> {
            Artist artist = getArtistById(id);
            log.info(artist);
            restTemplate.delete(String.format("%s/%s/%s", URL, ARTIST, id));
            log.info("Artist with id " + id + " was deleted!");
            return artist;
        });
    }

    @Override
    public Opera deleteWork(Integer id) {
        return execute(() -> {
            Opera opera = getOperaById(id);
            log.info(opera);
            restTemplate.delete(String.format("%s/%s/%s", URL, WORK, id));
            log.info("Work with id " + id + " was deleted");
            return opera;
        });
    }

    @Override
    public Artist updateArtist(Integer id, Artist artist) {
        Artist old = getArtistById(id);
        if (old == null) throw new ServiceException("Id " + id + " doesn't exist!");
        artist.setIdArtist(id);
        restTemplate.put(String.format("%s/%s/%s", URL, ARTIST, id), artist);
        return old;
    }

    public Artist getArtistById(Integer id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s/id=%s", URL, ARTIST, id), Artist.class));
    }

    @Override
    public Opera updateWork(Integer id, Opera op) {
        Opera old = getOperaById(id);
        if (old == null) throw new ServiceException("Id " + id + " doesn't exist!");
        op.setIdOpera(id);
        restTemplate.put(String.format("%s/%s/%s", URL, WORK, id), op);
        return old;
    }

    public Opera getOperaById(Integer id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s/id=%s", URL, WORK, id), Opera.class));
    }

    @Override
    public Opera findByTitle(String title) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn(e);
        }
        final String finalName = title;
        return execute(() -> restTemplate.getForObject(String.format("%s/%s/%s", URL, WORK, finalName), Opera.class));
    }

    @Override
    public Artist findByName(String name) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn(e);
        }
        final String finalName = name;
        return execute(() -> restTemplate.getForObject(String.format("%s/%s/%s", URL, ARTIST, finalName), Artist.class));
    }

    @Override
    public ArrayList<OperaDto> sortByTip() {
        OperaDto[] operaDto = execute(() ->
                restTemplate.getForObject(String.format("%s/%s/orderby=%s", URL, WORK, "tip"), OperaDto[].class));
        return new ArrayList<>(Arrays.asList(operaDto));
//        ArrayList<Opera> works = viewWorkss();
//        ArrayList<Artist> artists = viewArtistss();
//        return works.stream().sorted((n1, n2) -> n1.getTip().compareTo(n2.getTip()))
//                .map((opera) -> {
//                    OperaDto operaDto = new OperaDto();
//                    operaDto.id = opera.getIdOpera();
//                    operaDto.tip = opera.getTip();
//                    operaDto.workName = opera.getTitlu();
//                    Optional<Artist> artistRes = artists.stream()
//                            .filter((artist) -> artist.getIdArtist() == opera.getIdArtist()).findFirst();
//                    if (artistRes.isPresent()) {
//                        operaDto.artistName = artistRes.get().getNume();
//                    }
//                    return operaDto;
//                }).collect(Collectors.toList());
    }

    @Override
    public ArrayList<OperaDto> sortByTitle() {
        OperaDto[] operaDto = execute(() ->
                restTemplate.getForObject(String.format("%s/%s/orderby=%s", URL, WORK, "title"), OperaDto[].class));
        return new ArrayList<>(Arrays.asList(operaDto));
//        ArrayList<Opera> works = viewWorkss();
//        ArrayList<Artist> artists = viewArtistss();
//        return works.stream().sorted((n1, n2) -> n1.getTitlu().compareTo(n2.getTitlu()))
//                .map((opera) -> {
//                    OperaDto operaDto = new OperaDto();
//                    operaDto.id = opera.getIdOpera();
//                    operaDto.tip = opera.getTip();
//                    operaDto.workName = opera.getTitlu();
//                    Optional<Artist> artistRes = artists.stream()
//                            .filter((artist) -> artist.getIdArtist() == opera.getIdArtist()).findFirst();
//                    if (artistRes.isPresent()) {
//                        operaDto.artistName = artistRes.get().getNume();
//                    }
//                    return operaDto;
//                }).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Artist> sortByName() {
        return new ArrayList<>(Arrays.asList(execute(() ->
                restTemplate.getForObject(String.format("%s/%s/orderby=%s", URL, ARTIST, "name"), Artist[].class))));
    }

    @Override
    public ArrayList<Artist> sortByAnM() {
        return new ArrayList<>(Arrays.asList(execute(() ->
                restTemplate.getForObject(String.format("%s/%s/orderby=%s", URL, ARTIST, "death"), Artist[].class))));
    }

    @Override
    public ArrayList<Artist> sortByAnN() {
        return new ArrayList<>(Arrays.asList(execute(() ->
                restTemplate.getForObject(String.format("%s/%s/orderby=%s", URL, ARTIST, "birth"), Artist[].class))));
    }

    @Override
    public CrudRepository.Result[] close() throws IOException, InterruptedException, ExecutionException {
//        Future<CrudRepository.Result> result1 = executorService.submit(() -> {
//            return artistRepository.close();
//        });
//        Future<CrudRepository.Result> result2 = executorService.submit(() -> {
//            return operaRepository.close();
//        });
//        CrudRepository.Result[] res = new CrudRepository.Result[2];
//        res[0] = result1.get();
//        res[1] = result2.get();
//        return res;
        throw new UnsupportedOperationException("You can't do that!");
    }

    @Override
    public CrudRepository.Result[] start() throws InterruptedException, ExecutionException {
//        Future<CrudRepository.Result> result1 = executorService.submit(() -> {
//            return artistRepository.start();
//        });
//        Future<CrudRepository.Result> result2 = executorService.submit(() -> {
//            return operaRepository.start();
//        });
//        CrudRepository.Result[] res = new CrudRepository.Result[2];
//        res[0] = result1.get();
//        res[1] = result2.get();
//        return res;
        throw new UnsupportedOperationException("You can't do that!");
    }

    private <T> T execute(Callable<T> callable) throws ServiceException {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
*/
    public void setUser(User user) {
        this.user = user;
    }

    public void viewPosts() {
    }
}

public class FileStructure {
    String title;
    String urlMp4;
    String name;
    String description;
    String thumbnailUrl; // Image URL
    String uploadDate;
    String duration;
    String contentUrl;
    String embedUrl;

    public FileStructure(String title, String urlMp4, String name, String description, String thumbnailUrl, String uploadDate, String duration, String contentUrl, String embedUrl) {
        this.title = title;
        this.urlMp4 = urlMp4;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadDate = uploadDate;
        this.duration = duration;
        this.contentUrl = contentUrl;
        this.embedUrl = embedUrl;
    }

    public FileStructure() {

    }

    public String getTitle(){ return title; }
    public String getUrlMp4(){ return urlMp4; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String getThumbnailUrl(){ return thumbnailUrl; }
    public String getUploadDate(){ return uploadDate; }
    public String getDuration(){ return duration; }
    public String getContentUrl(){ return contentUrl; }
    public String getEmbedUrl(){ return embedUrl; }

    public void setTitle(String title) { this.title = title;}
    public void setUrlMp4(String urlMp4) {this.urlMp4 = urlMp4;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setThumbnailUrl(String thumbnailUrl) {this.thumbnailUrl = thumbnailUrl;}
    public void setUploadDate(String uploadDate) {this.uploadDate = uploadDate;}
    public void setDuration(String duration) {this.duration = duration;}
    public void setContentUrl(String contentUrl) {this.contentUrl = contentUrl;}
    public void setEmbedUrl(String embedUrl) {this.embedUrl = embedUrl;}
}


/*
<script type="application/ld+json">
            {"@context":"https://schema.org",
            "@type":"VideoObject",
            "name":"Fernando Tatis Jr. crushes game-tying HR in the 3rd",
            "description":"Listen to the radio call of Fernando Tatis Jr.'s game-tying solo home run in the 3rd inning",
            "thumbnailUrl":"https://img.mlbstatic.com/mlb-images/image/upload/ar_16:9,g_auto,q_auto:good,w_1536,c_fill,f_jpg/mlb/pbjp5o2xqp0xugeo3iwt",
            "uploadDate":"2026-10-07",
            "duration":"P0Y0M0DT0H0M30S",
            "contentUrl":"https://mlb-cuts-diamond.mlb.com/FORGE/2026/2026-10/07/53c4a4cd-29652647-aff09da3-csvm-diamondgcp-asset.m3u8",
            "embedUrl":"https://streamable.com/m/fernando-tatis-jr-crushes-game-tying-hr-in-the-3rd"}
        </script>
* */
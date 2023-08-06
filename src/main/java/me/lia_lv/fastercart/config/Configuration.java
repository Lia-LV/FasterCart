/*
!  This class uses Osiris Team's Dyml,
!  so the following copyright and license notation is attached.

MIT License

Copyright (c) 2021 Osiris Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package me.lia_lv.fastercart.config;

import com.cryptomorin.xseries.XMaterial;
import com.osiris.dyml.Yaml;
import com.osiris.dyml.YamlSection;
import com.osiris.dyml.exceptions.*;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class Configuration {

    private JavaPlugin plugin;
    private final Yaml config;

    public Configuration(JavaPlugin plugin, String fileName) throws YamlReaderException, IOException, DuplicateKeyException, IllegalListException {
        this(new File(plugin.getDataFolder(), fileName));
        this.plugin = plugin;
    }

    public Configuration(JavaPlugin plugin, String path, String fileName) throws YamlReaderException, IOException, DuplicateKeyException, IllegalListException {
        this(new File(plugin.getDataFolder().toString() + File.separatorChar + path, fileName));
        this.plugin = plugin;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Configuration(File configFile) throws YamlReaderException, IOException, DuplicateKeyException, IllegalListException {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        }

        this.config = new Yaml(configFile);
        Yaml.charset = StandardCharsets.UTF_8;
        this.config.load();
    }

    private String[] pathSplit(String path) {
        return path.split("\\.");
    }

    private boolean isPathSplitRequired(String path) {
        return path.contains(".");
    }

    public void save(boolean overwrite) throws YamlReaderException, YamlWriterException, IOException, DuplicateKeyException, IllegalListException {
        this.config.save(overwrite);
    }

    public void saveAndLoad() throws YamlReaderException, YamlWriterException, IOException, DuplicateKeyException, IllegalListException {
        this.config.saveAndLoad();
    }

    private boolean isValueNull(YamlSection value) {
        return value.getValue() == null;
    }

    public void addCommentsOnly(String path, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        this.config.put(path).setComments(comments).setCountTopLineBreaks(topLineBreaks);
    }

    public Material get(String path, Material defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }
        Optional<XMaterial> xMat = XMaterial.matchXMaterial(yamlSection.getValue().asString());
        Material mat;
        if (xMat.isPresent()) {
            mat = xMat.get().parseMaterial();
        } else {
            mat = defaultValue;
        }

        return isValueNull(yamlSection) ? defaultValue : mat;
    }

    public List<String> get(String path, String[] defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }

        return isValueNull(yamlSection) ? List.of(defaultValue) : yamlSection.asStringList();
    }

    public String get(String path, String defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }

        return isValueNull(yamlSection) ? defaultValue : yamlSection.getValue().asString();
    }

    public int get(String path, Integer defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }

        return isValueNull(yamlSection) ? defaultValue : yamlSection.getValue().asInt();
    }

    public double get(String path, Double defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }

        return isValueNull(yamlSection) ? defaultValue : yamlSection.getValue().asDouble();
    }

    public Boolean get(String path, Boolean defaultValue, int topLineBreaks, String... comments) throws NotLoadedException, IllegalKeyException {
        YamlSection yamlSection;
        if (isPathSplitRequired(path)) {
            yamlSection = this.config.put(pathSplit(path)).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        } else {
            yamlSection = this.config.put(path).setDefValues(defaultValue.toString()).setCountTopLineBreaks(topLineBreaks).setComments(comments);
        }

        return isValueNull(yamlSection) ? defaultValue : yamlSection.getValue().asBoolean();
    }


}

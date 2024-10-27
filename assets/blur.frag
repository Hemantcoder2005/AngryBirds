#version 130
uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform float u_blurAmount;

varying vec2 v_texCoord;

void main() {
    vec2 texOffset = u_blurAmount / u_resolution; // gets size of one texel
    vec4 result = vec4(0.0);

    // Sample the surrounding pixels to create a blur effect
    for(int x = -1; x <= 1; x++) {
        for(int y = -1; y <= 1; y++) {
            vec2 offset = vec2(x, y) * texOffset;
            result += texture2D(u_texture, v_texCoord + offset);
        }
    }

    gl_FragColor = result / 9.0; // Average the color
}
